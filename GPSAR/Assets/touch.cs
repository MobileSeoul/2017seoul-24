using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class touch : MonoBehaviour {

    private GameObject target;
    AndroidJavaObject mnd;
    bool Touched=false;


    private void Start()
    {
        AndroidJNI.AttachCurrentThread();
    
        
    }
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            target = GetClickedObject();

            if (target.Equals(gameObject)&&Touched==false)  //선택된게 해당 오브젝트이고 터치된 적이 없을 시 실행
            {

                /* var tt = GameObject.Find("Get_text");      //오브젝트의 애니메이션을 불러온다.
                 tt.GetComponent<Animation>().Play("Get");     //텍스트 애니메이션 "Get"을 실행한다. */
                this.GetComponent<Animation>().Play("Rotate");   //오브젝트 애니메이션 "Rotate"를 실행한다.
                this.GetComponent<AudioSource>().Play() ;                     //터치 소리를 실행한다.*

                                                                

                Invoke("AQ", 3);  // 3초후 //안드로이드 Native Activity onTouch()메소드 호출 및 어플리케이션 종료
                Touched = true; // 연속 터치 방지


            }


        }

        
    }

    private GameObject GetClickedObject()
    {
        RaycastHit hit;
        GameObject target = null;


        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition); //터치 포인트 근처 좌표를 만든다. 


        if (true == (Physics.Raycast(ray.origin, ray.direction * 10, out hit)))   //터치포인트 근처에 오브젝트가 있는지 확인
        {
            //있으면 오브젝트를 저장한다.
            target = hit.collider.gameObject;
            GameObject.Find("Button").SetActive(false);
        }

        return target;
    }
    private void AQ() // 어플리케이션 종료 함수
    {
        AndroidJavaClass tc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        mnd = tc.GetStatic<AndroidJavaObject>("currentActivity");
        mnd.Call("onTouch", GPS.Instance.question_code); //Native Call
        
    }
}


