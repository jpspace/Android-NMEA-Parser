package uiseok.skyplotview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import uiseok.data.SatelliteData;
import uiseok.util.TypedValueCalculate;

/**
 * Created by uiseok on 2016-09-16.
 */
public class SkyPlotView extends View {

    Context mContext;

    public SkyPlotView(Context context) {
        super(context);
        init(context);
    }

    public SkyPlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager wm;
    int window_width_in_pixels = 0;
    int window_height_in_pixels = 0;
    int window_width_in_dp = 0;
    int window_height_in_dp = 0;
    double center_point_x = 0;
    double center_point_y = 0;


    Paint gridPaint, backgroundPaint, textPaint, dotGridPaint;
    Paint before_satellite_paint, current_satellite_paint;


    List<SatelliteData> satelliteList;

    // Flag
    boolean showDegree = true;

    private void init(Context context) {
        // Initialize
        mContext = context;
        gridPaint = new Paint();
        gridPaint.setStrokeWidth(TypedValueCalculate.dp2pixel(2,mContext));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(Color.rgb(68, 135, 193));

        dotGridPaint = new Paint();
        dotGridPaint.setStrokeWidth(TypedValueCalculate.dp2pixel(1,mContext));
        dotGridPaint.setStyle(Paint.Style.STROKE);
        dotGridPaint.setColor(Color.rgb(68, 135, 193));
        dotGridPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));



        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TypedValueCalculate.dp2pixel(12,mContext));
        textPaint.setTextAlign(Paint.Align.CENTER);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        before_satellite_paint = new Paint();
        before_satellite_paint.setColor(Color.GRAY);
        current_satellite_paint = new Paint();
        current_satellite_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        current_satellite_paint.setColor(Color.RED);


        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        window_width_in_pixels = metrics.widthPixels;
        window_height_in_pixels = metrics.heightPixels;
        window_width_in_dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, window_width_in_pixels, metrics);
        window_height_in_dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, window_height_in_pixels, metrics);


        satelliteList = new ArrayList<SatelliteData>();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        center_point_x = parentWidth/2;
        center_point_y = parentHeight/2;

    }

    SatelliteData newData;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // Draw SkyPlot Grid
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4), gridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4 * Math.cos(Math.PI / 180 * 15)), dotGridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4 * Math.cos(Math.PI / 180 * 30)), dotGridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4 * Math.cos(Math.PI / 180 * 45)), dotGridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4 * Math.cos(Math.PI / 180 * 60)), dotGridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4 * Math.cos(Math.PI / 180 * 75)), dotGridPaint);

        canvas.drawLine((float) center_point_x, (float) (center_point_y - (center_point_x * 3 / 4)), (float) center_point_x, (float) (center_point_y + center_point_x * 3 / 4), dotGridPaint);
        canvas.drawLine((float) center_point_x/4, (float) center_point_y , (float) center_point_x*7/4, (float) center_point_y , dotGridPaint);


        if (showDegree) {

            // Draw Elevation degree
            canvas.drawText("0˚", (float) center_point_x+TypedValueCalculate.dp2pixel(2,mContext), (float) (center_point_y - center_point_x * 3 / 4 - TypedValueCalculate.dp2pixel(5,mContext)), textPaint);
            canvas.drawText("30˚", (float) center_point_x+TypedValueCalculate.dp2pixel(2,mContext), (float) (center_point_y-center_point_x*3/4*Math.cos(Math.PI/180*30)+TypedValueCalculate.dp2pixel(5,mContext)), textPaint);
            canvas.drawText("60˚", (float) center_point_x+TypedValueCalculate.dp2pixel(2,mContext), (float) (center_point_y-center_point_x*3/4*Math.cos(Math.PI/180*60)+TypedValueCalculate.dp2pixel(5,mContext)), textPaint);
            // Draw Azimuth degree
            canvas.drawText("270˚", (float) (center_point_x / 4 - TypedValueCalculate.dp2pixel(15,mContext)), (float) center_point_y + TypedValueCalculate.dp2pixel(3,mContext), textPaint);
            canvas.drawText("180˚", (float) center_point_x, (float) (center_point_y + center_point_x * 3 / 4 + TypedValueCalculate.dp2pixel(15,mContext)), textPaint);
            canvas.drawText("90˚", (float) (center_point_x + center_point_x * 3 / 4 + TypedValueCalculate.dp2pixel(15,mContext)), (float) center_point_y + TypedValueCalculate.dp2pixel(3,mContext), textPaint);

        }

        if (newData != null) {
            for (int i = 0; i < satelliteList.size(); i++) {
                draw_calculated_Elev_Azi_to_X_Y_Coord(canvas, satelliteList.get(i).elevation_in_degree, satelliteList.get(i).azimuth_in_degree, TypedValueCalculate.dp2pixel(4,mContext), satelliteList.get(i).satellite_num ,before_satellite_paint);
            }
            draw_calculated_Elev_Azi_to_X_Y_Coord(canvas, newData.elevation_in_degree, newData.azimuth_in_degree, TypedValueCalculate.dp2pixel(4,mContext),newData.satellite_num, current_satellite_paint);
        }


    }

    private void draw_calculated_Elev_Azi_to_X_Y_Coord(Canvas canvas, double elevation, double azimuth_in_degree, int circle_size, String satellite_num ,Paint paint) {
        double x = 0, y = 0;
        // calculate position
        x = center_point_x * 3 / 4 * Math.cos(elevation * Math.PI / 180) * Math.sin(azimuth_in_degree * Math.PI / 180);
        y = -(center_point_x * 3 / 4 * Math.cos(elevation * Math.PI / 180) * Math.cos(azimuth_in_degree * Math.PI / 180));
        // move zero point
        x = x + center_point_x;
        y = y + center_point_y;
        // draw satellite
        canvas.drawCircle((float) x, (float) y, circle_size, paint);
        canvas.drawText(satellite_num, (float)x, (float)y-TypedValueCalculate.dp2pixel(3, mContext), textPaint);
    }


    public void drawSatellite(double elevation_in_degree, double azimuth_in_degree, String satellite_num) {
        newData = new SatelliteData(elevation_in_degree, azimuth_in_degree, "G"+satellite_num);
        satelliteList.add(newData);
        invalidate();
    }

    public void showDegree(boolean show) {
        this.showDegree = show;
        invalidate();
    }

    public void setTextPaint(Paint paint) {
        textPaint = paint;
        invalidate();
    }

    public void setGridPaint(Paint paint) {
        gridPaint = paint;
        invalidate();
    }

    public void setDotGridPaint(Paint paint){
        dotGridPaint = paint;
        invalidate();
    }

    public void setBefore_satellite_paint(Paint paint) {
        before_satellite_paint = paint;
        invalidate();
    }

    public void setCurrent_satellite_paint(Paint paint) {
        current_satellite_paint = paint;
        invalidate();
    }


}
