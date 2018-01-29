package com.hbag.seoulhang.network;

import android.util.Log;

import com.hbag.seoulhang.model.retrofit.DataBaseDTO;
import com.hbag.seoulhang.model.retrofit.FindDTO;
import com.hbag.seoulhang.model.retrofit.HintDTO;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.model.retrofit.ItemDTO;
import com.hbag.seoulhang.model.retrofit.NoticeDTO;
import com.hbag.seoulhang.model.retrofit.PlayerDTO;
import com.hbag.seoulhang.model.retrofit.QuestDTO;
import com.hbag.seoulhang.model.retrofit.RankDTO;
import com.hbag.seoulhang.model.retrofit.RateDTO;
import com.hbag.seoulhang.model.retrofit.TopDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static NetworkClient instance = null;
    private Retrofit retrofit;
    private NetworkService service;

    private NetworkClient(String serverAddress) {
        Log.e("debug", "NetworkClient Constructor : " + serverAddress);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(serverAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(NetworkService.class);
    }

    public static NetworkClient getInstance(String serverAddress) {
        if (instance == null) {
            instance = new NetworkClient(serverAddress);
        }
        return instance;
    }

    public static NetworkClient getInstance(){
        return instance;
    }

    public void login(String player, String password, Callback<PlayerDTO> cb){
        Call<PlayerDTO> call = service.login(player,password);
        call.enqueue(cb);
    }

    public void getquestioncode(String player, int question_code, Callback<Integer> callback){
        Call <Integer> call = service.getquestioncode(player,question_code);
        call.enqueue(callback);
    }
    public void getstartitem(String player, Callback<List<ItemDTO>> callback){
        Call<List<ItemDTO>> call = service.getstartitem(player);
        call.enqueue(callback);
    }

    public void checkplayer(String player,int question_code, Callback<Integer> callback){
        Call<Integer> call = service.checkplayer(player, question_code);
        call.enqueue(callback);
    }

    public void checkanswer(String player, int questioncode, Callback<Integer> callback){
        Call<Integer> call = service.checkanswer(player,questioncode);
        call.enqueue(callback);
    }
    public void getranking(String player , Callback<List<RankDTO>> callback){
        Call<List<RankDTO>> call = service.getranking(player);
        call.enqueue(callback);
    }

    public void getfinishitem(String player, Callback<List<InventoryDTO>> callback){
        Call<List<InventoryDTO>> call = service.getfinishitem(player);
        call.enqueue(callback);
    }
    public void getrate(String player, Callback<List<RateDTO>> callback){
        Call<List<RateDTO>> call = service.getrate(player);
        call.enqueue(callback);
    }

    public void gethint(String player, int questioncode, String check, Callback<HintDTO> callback){
        Call<HintDTO> call = service.gethint(player,questioncode, check);
        call.enqueue(callback);
    }

    public void setlanguage(String player, int language, Callback<Integer> callback){
        Call<Integer> call = service.setlanguage(player, language);
        call.enqueue(callback);
    }

    public void getjoin(String player, String password, String name, String email, String logininfo, Callback<String> callback){
        Call<String> call = service.getjoin(player, password, name, email, logininfo);
        call.enqueue(callback);
    }
    public void checkid(String player,Callback<String> callback){
        Call<String> call = service.checkid(player);
        call.enqueue(callback);
    }

    public void findid(String name, String email, Callback<FindDTO> callback){
        Call<FindDTO> call = service.findid(name, email);
        call.enqueue(callback);
    }
    public void findpassword(String name, String email,String id, Callback<FindDTO> callback){
        Call<FindDTO> call = service.findpassword(name, email, id);
        call.enqueue(callback);
    }
    public void entercode(String id, String code, Callback<FindDTO> callback){
        Call<FindDTO> call = service.entercode(id, code);
        call.enqueue(callback);
    }
    public void newpassword(String id, String password, Callback<FindDTO> callback){
        Call<FindDTO> call = service.newpassword(id, password);
        call.enqueue(callback);
    }
    public void withdrawal(String id, String password, Callback<String> callback){
        Call<String> call = service.withdrawal(id, password);
        call.enqueue(callback);
    }
    public void realwithdrawal(String id, Callback<String>callback){
        Call<String> call = service.realwithdrawal(id);
        call.enqueue(callback);
    }

    public void editprofile(String id, String password, String nickname, String email, Callback<String> callback){
        Call<String> call = service.editprofile(id, password, nickname, email);
        call.enqueue(callback);
    }

    public void quiznum(String id, Callback<List<Integer>> callback){
        Call<List<Integer>> call = service.quiznum(id);
        call.enqueue(callback);
    }
    public void startgame(String id, Callback<Integer> callback){
        Call<Integer> call = service.startgame(id);
        call.enqueue(callback);
    }
    public void newnickname(String id, String nickname, Callback<String> callback){
        Call<String> call = service.newnickname(id, nickname);
        call.enqueue(callback);
    }
    public void toptenregion(String id, Callback<List<TopDTO>> callback){
        Call<List<TopDTO>> call = service.toptenregion(id);
        call.enqueue(callback);
    }

    public void notice(String id,Callback<List<NoticeDTO>> callback){
        Call<List<NoticeDTO>> call = service.notice(id);
        call.enqueue(callback);
    }

    public void grade(String id, Callback<List<Integer>> callback){
        Call<List<Integer>> call = service.grade(id);
        call.enqueue(callback);
    }

    public void make_quiz(String id,
                          String question_name,
                          double x_coordinate,
                          double y_coordinate,
                          String question,
                          String answer,
                          String hint,
                          String locale,
                          Callback<Integer> callback){
        Call<Integer> call = service.make_quiz(id, question_name, x_coordinate, y_coordinate,question, answer, hint, locale);
        call.enqueue(callback);
    }

    public void send_db(String id, Callback<List<DataBaseDTO>> callback){
        Call<List<DataBaseDTO>> call = service.send_db(id);
        call.enqueue(callback);
    }


    public void ox_flag(String id, int quesion_code, Callback<String> callback){
        Call<String> call = service.ox_flag(id,quesion_code);
        call.enqueue(callback);
    }

}
