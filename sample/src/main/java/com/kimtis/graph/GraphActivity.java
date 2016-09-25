package com.kimtis.graph;

import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kimtis.R;

import java.util.Random;

import uiseok.util.TypedValueCalculate;

public class GraphActivity extends AppCompatActivity {

    GraphView graphN, graphE, graphV;

    LocationManager mLM;


    TextView btn_stop, btn_start, btn_change;

    LineGraphSeries<DataPoint> n_series, e_series, v_series;

    LinearLayout linear_nev, linear_plot;
    TextView text_error;

    int i=0;
    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble() - mRand.nextDouble();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        linear_nev = (LinearLayout)findViewById(R.id.linear_nev);
        linear_plot = (LinearLayout)findViewById(R.id.linear_plot);
        text_error = (TextView)findViewById(R.id.text_error);


        graphN = (GraphView)findViewById(R.id.graph_n);
        graphE = (GraphView)findViewById(R.id.graph_e);
        graphV = (GraphView)findViewById(R.id.graph_v);

        // N_Graph Settings
        final Viewport n_viewport = graphN.getViewport();
        final LegendRenderer nlegendRenderer = graphN.getLegendRenderer();
        final GridLabelRenderer nlabelRenderer = graphN.getGridLabelRenderer();

        n_viewport.setScrollable(true);
        n_viewport.setScalable(true);
        n_viewport.setMinX(0);
        n_viewport.setDrawBorder(true);
        n_viewport.setBorderColor(Color.BLACK);
        graphN.setPadding(TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),0);
        graphN.setTitle("Error with N");
        nlabelRenderer.setHighlightZeroLines(true);
        nlabelRenderer.setVerticalLabelsVisible(true);
        nlabelRenderer.setVerticalAxisTitle(" ");


        n_series = new LineGraphSeries<DataPoint>();
        n_series.setAnimated(true);
        n_series.setDrawDataPoints(false);
        graphN.addSeries(n_series);

        // E_Graph Settings
        final Viewport e_viewport = graphE.getViewport();
        final LegendRenderer elegendRenderer = graphE.getLegendRenderer();
        final GridLabelRenderer elabelRenderer = graphE.getGridLabelRenderer();

        e_viewport.setScrollable(true);
        e_viewport.setScalable(true);
        e_viewport.setMinX(0);
        e_viewport.setDrawBorder(true);
        e_viewport.setBorderColor(Color.BLACK);
        graphE.setPadding(TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),0);
        graphE.setTitle("Error with E");
        elabelRenderer.setHighlightZeroLines(true);
        elabelRenderer.setHighlightZeroLines(true);
        elabelRenderer.setVerticalLabelsVisible(true);
        elabelRenderer.setVerticalAxisTitle(" ");

        e_series = new LineGraphSeries<DataPoint>();
        e_series.setAnimated(true);
        e_series.setDrawDataPoints(false);
        graphE.addSeries(e_series);


        // V_Graph Settings
        final Viewport v_viewport = graphV.getViewport();
        final LegendRenderer vlegendRenderer = graphV.getLegendRenderer();
        final GridLabelRenderer vlabelRenderer = graphV.getGridLabelRenderer();

        v_viewport.setScrollable(true);
        v_viewport.setScalable(true);
        v_viewport.setMinX(0);
        v_viewport.setDrawBorder(true);
        v_viewport.setBorderColor(Color.BLACK);
        graphV.setPadding(TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),0);
        graphV.setTitle("Error with V");
        vlabelRenderer.setHighlightZeroLines(true);
        vlabelRenderer.setHighlightZeroLines(true);
        vlabelRenderer.setVerticalLabelsVisible(true);
        vlabelRenderer.setVerticalAxisTitle(" ");

        v_series = new LineGraphSeries<DataPoint>();
        v_series.setAnimated(true);
        v_series.setDrawDataPoints(false);
        graphV.addSeries(v_series);








        btn_start = (TextView)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_stop=(TextView)findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_change = (TextView)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linear_nev.getVisibility() == LinearLayout.VISIBLE) {
                    linear_nev.setVisibility(LinearLayout.GONE);
                    linear_plot.setVisibility(LinearLayout.VISIBLE);
                }else{
                    linear_nev.setVisibility(LinearLayout.VISIBLE);
                    linear_plot.setVisibility(LinearLayout.GONE);
                }
            }
        });


    }

}
