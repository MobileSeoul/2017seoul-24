package com.hbag.seoulhang.network;

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

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkService {
    @GET("/players/{player}/password/{password}")
    Call<PlayerDTO> login(@Path("player") String player,@Path("password") String password);

    @GET("/collect/{player}/call_code/{question_code}")
    Call<Integer> getquestioncode(@Path("player") String player, @Path("question_code") int question_code);

    @GET("/start_player/{player}")
    Call<List<ItemDTO>> getstartitem(@Path("player") String player);

    @GET("/finish_player/{player}")
    Call<List<InventoryDTO>> getfinishitem(@Path("player") String player);

    @GET("/check_player/{player}/question_code/{question_code}")
    Call<Integer> checkplayer(@Path("player") String player, @Path("question_code")int question_code);

    @GET("/update_player/{player}/call_code/{questioncode}")
    Call<Integer> checkanswer(@Path("player") String player, @Path("questioncode") int questioncode);

    @GET("/ranking/{player}")
    Call <List<RankDTO>> getranking(@Path("player") String player);

    @GET("/rate/{player}")
    Call <List<RateDTO>> getrate(@Path("player") String plyer);

    @GET("/hint_player/{player}/call_code/{questioncode}/check/{check}")
    Call <HintDTO> gethint(@Path("player") String player, @Path("questioncode") int questioncode,@Path("check") String check);

    @GET("/setting_language/{player}/language/{language}")
    Call <Integer> setlanguage(@Path("player") String player, @Path("language") int language);

    @GET("/id/{player}/pass/{password}/name/{name}/email/{email}/logininfo/{logininfo}")
    Call <String> getjoin(@Path("player") String player, @Path("password") String password, @Path("name") String name, @Path("email") String email, @Path("logininfo") String logininfo);

    @GET("/checkid/{player}")
    Call<String> checkid(@Path("player") String player);

    @GET("/name/{name}/email/{email}")
    Call<FindDTO> findid(@Path("name") String name, @Path("email") String email);

    @GET("/name/{name}/email/{email}/id/{id}")
    Call<FindDTO> findpassword(@Path("name") String name, @Path("email") String email, @Path("id") String id);

    @GET("/id/{id}/code/{code}")
    Call<FindDTO> entercode(@Path("id") String id, @Path("code") String code);

    @GET("/id/{id}/password/{password}")
    Call<FindDTO> newpassword(@Path ("id") String id, @Path("password") String password);

    @GET("/withdrawal/id/{id}/password/{password}")
    Call<String> withdrawal(@Path ("id") String id, @Path("password") String password);

    @GET("/realwithdrawal/id/{id}")
    Call<String> realwithdrawal(@Path("id") String id);

    @GET("/editprofile/id/{id}/password/{password}/nickname/{nickname}/email/{email}")
    Call<String> editprofile(@Path ("id") String id, @Path("password") String password, @Path("nickname") String name, @Path("email") String email);

    @GET("/quiznum/id/{id}")
    Call<List<Integer>> quiznum(@Path ("id") String id);

    @GET("/startgame/id/{id}")
    Call<Integer> startgame(@Path ("id") String id);

    @GET("/newnickname/id/{id}/nickname/{nickname}")
    Call<String> newnickname(@Path("id") String id, @Path ("nickname") String nickname);

    @GET("/toptenregion/id/{id}")
    Call<List<TopDTO>> toptenregion(@Path("id") String id);

    @GET("/notice/id/{id}")
    Call<List<NoticeDTO>>notice(@Path("id")String id);

    @GET("/grade/id/{id}")
    Call<List<Integer>>grade(@Path("id")String id);

    @GET("/make_quiz/id/{id}/question_name/{question_name}/x/{x_coordinate}/y/{y_coordinate}/question/{question}/answer/{answer}/hint/{hint}/locale/{locale}")
    Call<Integer>make_quiz(@Path("id") String id,
                          @Path("question_name") String question_name,
                          @Path("x_coordinate") double x_coordinate,
                          @Path("y_coordinate") double y_coordinate,
                          @Path("question") String question,
                          @Path("answer") String answer,
                          @Path("hint") String hint,
                          @Path("locale") String locale);

    @GET("/send_db/id/{id}")
    Call<List<DataBaseDTO>>send_db(@Path("id") String id);

    @GET("/ok_false/id/{id}/question_code/{question_code}")
    Call<String>ox_flag(@Path("id")String id, @Path("question_code") int question_code);

}
