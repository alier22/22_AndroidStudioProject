package com.example.domtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textview);
    }
    public void onClick(View view){
        GetXMLTask task = new GetXMLTask(this);
        task.execute("https://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");
    }


    class GetXMLTask extends AsyncTask<String, Void, Document> {
        private Activity context;

        public GetXMLTask(Activity context) {
            this.context = context;
        }

        @Override
        protected Document doInBackground(String... strings) {
            URL url;
            Document document = null;
            String s = null;
            byte[] buffer = new byte[10000];

            try {
                //url 다운로드
                url = new URL(strings[0]);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                document = db.parse(new InputSource(url.openStream()));
                document.getDocumentElement().normalize();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            String s = "";
            NodeList nodeList = document.getElementsByTagName("data");

            //day, hour, temp, wfkor출력
            for (int i = 0; i < nodeList.getLength(); i++) {
                s += "" + i + ":날씨 정보: ";
                Node node = nodeList.item(i);
                Element element = (Element) node;
                //temp
                NodeList nameList = document.getElementsByTagName("temp");
                Element nameelement = (Element) nameList.item(0);
                nameList = nameelement.getChildNodes();

                s += "온도 = " + ((Node) nameList.item(0)).getNodeValue() + ",\t";

                //day
                NodeList dayList = document.getElementsByTagName("day");
                Element dayelement = (Element) dayList.item(0);
                dayList = dayelement.getChildNodes();

                s += "날짜 = " + ((Node) dayList.item(0)).getNodeValue() + ",\t";

                //wfkor
                NodeList websiteList = document.getElementsByTagName("wfkor");
                Element websiteelement = (Element) websiteList.item(0);
                websiteList = websiteelement.getChildNodes();

                s += "날짜 = " + ((Node) websiteList.item(0)).getNodeValue() + ",\t";
                //hour
                NodeList timeList = document.getElementsByTagName("hour");
                Element timeelement = (Element) timeList.item(0);
                timeList = timeelement.getChildNodes();

                s += "시간 = " + ((Node) timeList.item(0)).getNodeValue() + ",\t";

            }
            textview.setText(s);
        }
    }
}