package org.techtown.boostcamp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.techtown.boostcamp.data.MovieInfo;
import org.techtown.boostcamp.data.MovieList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    String query;
    Handler handler = new Handler();
    MovieAdapter adapter;
    StringBuffer response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(getApplicationContext());


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("hihihi", "클릭 시작");
                query = editText.getText().toString();

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                    //    Log.d("hihihi", "run");

                    //    Log.d("hihihi", "" + query);

                        try {
                            String clientId = "DhU8jxH1HY7DqrUlVmrr";//애플리케이션 클라이언트 아이디값";
                            String clientSecret = "ErVxxUCc10";//애플리케이션 클라이언트 시크릿값";
                            String text = URLEncoder.encode("엑스맨", "UTF-8");
                            String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text; // json 결과
                            URL url = new URL(apiURL);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            con.setRequestProperty("X-Naver-Client-Id", clientId);
                            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                          //  Log.d("hihihi", "아직");
                            int responseCode = con.getResponseCode();
                           // Log.d("hihihi", "1");
                            BufferedReader br;
                            if (responseCode == 200) { // 정상 호출
                                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            } else {  // 에러 발생
                                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                            }
                            String inputLine;
                            response = new StringBuffer();
                            while ((inputLine = br.readLine()) != null) {
                                response.append(inputLine);
                            }
                            //Log.d("hihihi", "" + response.toString());// response안에 json으로 드렁가 있음!

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    processResponse(response.toString());

                                    //textView.setText("현재 값 : "+value);
                                    //여기 안에서 gson으로 변환한걸 데이터를 넣어줘야함 리싸이클러뷰에!
                                }
                            });
                            br.close();
                        } catch (Exception e) {
                            //Log.d("hihihi", "" + e);

                        }

                    }


                }).start();

            }
        });
    }


    public void processResponse(String response) {
        Gson gson = new Gson();
        response=response.replaceAll("<b>","");
        response=response.replaceAll("</b>","");


        MovieList movieList = gson.fromJson(response, MovieList.class);
       // Log.d("hihihi", "들어왔냐\n"+movieList.items);
        //MovieInfo movieInfo = gson.fromJson(response, MovieInfo.class);
      //  Log.d("hihihi", "들어왔냐\n"+response);
        adapter=new MovieAdapter(getApplicationContext());


        for (int i = 0; i < movieList.items.size(); i++) {
            MovieInfo movieInfo = movieList.items.get(i);
           // Log.d("hihihi","끼룩끼룩"+movieInfo.title);
            //movieInfo.title=movieInfo.title.replaceAll("<b>","");
           // movieInfo.title=movieInfo.title.replaceAll("</b>","");
            adapter.addItem(new MovieItem(movieInfo.title,movieInfo.link,movieInfo.image,movieInfo.pubDate,movieInfo.director,movieInfo.actor,movieInfo.userRating));
        }
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieAdapter.ViewHolder holder, View view, int position) {
                MovieItem item=adapter.getItem(position);
                String url=item.getLink();//이 주소로 링크 띄우면 댐!!!
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
            }
        });
    }
}

