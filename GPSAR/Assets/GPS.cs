using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.Data;
using Mono.Data.SqliteClient;
using System.IO;

public class GPS : MonoBehaviour {

    public static GPS Instance { set; get; }
    public double latitude;
    public double longitude;
    public int region_code = 0;
    public string question_code="";
    public string region_name="";
    public string locale;
    public List<String> quizlist= new List<String>();
    AndroidJavaObject mnd;



    public void Start()
    {
        Instance = this;
        DontDestroyOnLoad(gameObject);
        StartCoroutine(StartLocationService());
        GameObject.Find("3d_user").SetActive(false);
        
    }


    private IEnumerator StartLocationService()
    {
        startLocale();

        if (!Input.location.isEnabledByUser)
        {
            Debug.Log("User has not enabled GPS");
            yield break;
        }
        Input.location.Start(10,1f);
        int maxWait = 20;
        while (Input.location.status == LocationServiceStatus.Initializing && maxWait > 0)
        {
            yield return new WaitForSeconds(1);
            maxWait--;
        }
        if (maxWait <= 0)
        {
            Debug.Log("Timed out");
            yield break;
        }
        if (Input.location.status == LocationServiceStatus.Failed)
        {
            Debug.Log("Unable to determin device location");
            yield break;
        }

        latitude = Input.location.lastData.latitude;
        longitude = Input.location.lastData.longitude;

        int region_c = 0;
        string region_n = "";
        int question_c =0 ;
        string filepath = "/data/user/0/com.hbag.seoulhang/databases/seoulhang.sqlite";

        if (!File.Exists(filepath))
        {
            WWW loadDB = new WWW("jar:file://" + Application.dataPath + "!/assets/" + "seoulhang.sqlite");
            while (!loadDB.isDone) { }
            File.WriteAllBytes(filepath, loadDB.bytes);
        }

        string _constr = "URI=file:" + filepath;
        Debug.Log(_constr);
        IDbConnection _dbc;
        IDbCommand _dbcm;
        IDataReader _dbr;

        _dbc = new SqliteConnection(_constr);
        _dbc.Open();
        _dbcm = _dbc.CreateCommand();
        if (locale == "ko")
        {
            _dbcm.CommandText = "SELECT region_code, question_name,question_code FROM questions WHERE (abs(x_coordinate-" + latitude + "))*(abs(x_coordinate-" + latitude + "))+(abs(y_coordinate-" + longitude + "))*(abs(y_coordinate-" + longitude + "))<=(0.002)*(0.002)"; //현재위치에서 500m안에 있는 역
        }
        else if (locale == "en")
        {
            _dbcm.CommandText = "SELECT region_code, question_name_en,question_code FROM questions WHERE (abs(x_coordinate-" + latitude + "))*(abs(x_coordinate-" + latitude + "))+(abs(y_coordinate-" + longitude + "))*(abs(y_coordinate-" + longitude + "))<=(0.002)*(0.002)"; //현재위치에서 500m안에 있는 역
        }

        _dbr = _dbcm.ExecuteReader();

        while (_dbr.Read())
        {
            region_c = _dbr.GetInt32(0);
            region_n = _dbr.GetString(1);       
            question_c = _dbr.GetInt32(2);
            if (!quizlist.Contains(question_c.ToString()))
            {
                region_code = region_c;
                region_name = region_n;
                question_code = question_c.ToString();
            }
 
        }  //이미 획득한 퀴즈를 제외한 퀴즈만 추가한다.
    

        if (region_code == 26 && locale == "ko")
            region_name = region_name + "님의 퀴즈";
        else if (region_code == 26 && locale == "en")
            region_name = region_name + "'s Quiz";
        Debug.Log(region_name);

        if (region_name == "")         //gps에 해당되지 않는 위치일시 오브젝트 비활성화
        {
            if (locale == "ko")
            {
                region_name = "주변에 타겟이 없습니다.";
            }
            else if (locale == "en")
            {
                region_name = "There is no target nearby.";
            }
        }
        else
        {
                GameObject.Find("GameObject").transform.Find("3d_user").gameObject.SetActive(true);
        } 

        yield break;
    }

    private void startLocale()
    {
        AndroidJavaClass tc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        mnd = tc.GetStatic<AndroidJavaObject>("currentActivity");
        mnd.Call("checkLocale"); //안드로이드의 checkLocale 함수를 호출한다.
    }
    private void checkLocale(String lang)  //안드로이드의 checkLocale함수로 부터 lang을 받아서 언어 설정을 한다.
    {
        locale = lang;
    }
    private void getQuizlist(String q_code)
    {
        quizlist.Add(q_code);
        Debug.Log(q_code);
        for(int i=0; i<quizlist.Count; i++)
        {
            Debug.Log(quizlist[i]);
        }
    }
}
