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

import uiseok.data.NEVData;
import uiseok.util.TypedValueCalculate;

/**
 * Created by uiseok on 2016-09-16.
 */
public class SkyPlotViewCustom extends View {

    Context mContext;

    public static final int TYPE_BEFORE = 0;
    public static final int TYPE_MODIFIED = 1;

    public SkyPlotViewCustom(Context context) {
        super(context);
        init(context);
    }

    public SkyPlotViewCustom(Context context, AttributeSet attrs) {
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
    Paint basic_point_paint, other_point_paint;


    List<NEVData> nevList;

    // Flag
    boolean showMeters = true;

    private void init(Context context) {
        // Initialize
        mContext = context;
        gridPaint = new Paint();
        gridPaint.setStrokeWidth(TypedValueCalculate.dp2pixel(2, mContext));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(Color.rgb(68, 135, 193));

        dotGridPaint = new Paint();
        dotGridPaint.setStrokeWidth(TypedValueCalculate.dp2pixel(1, mContext));
        dotGridPaint.setStyle(Paint.Style.STROKE);
        dotGridPaint.setColor(Color.rgb(68, 135, 193));
        dotGridPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));


        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TypedValueCalculate.dp2pixel(12, mContext));
        textPaint.setTextAlign(Paint.Align.CENTER);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        basic_point_paint = new Paint();
        basic_point_paint.setColor(Color.GRAY);
        other_point_paint = new Paint();
        other_point_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        other_point_paint.setColor(Color.RED);


        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        window_width_in_pixels = metrics.widthPixels;
        window_height_in_pixels = metrics.heightPixels;
        window_width_in_dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, window_width_in_pixels, metrics);
        window_height_in_dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, window_height_in_pixels, metrics);


        nevList = new ArrayList<NEVData>();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        center_point_x = parentWidth / 2;
        center_point_y = parentHeight / 2;

    }

    NEVData newData;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        // Draw SkyPlot Grid
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 3 / 4), gridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 2 / 4), dotGridPaint);
        canvas.drawCircle((float) center_point_x, (float) center_point_y, (float) (center_point_x * 1 / 4), dotGridPaint);
        canvas.drawLine((float) center_point_x, (float) (center_point_y - (center_point_x * 3 / 4)), (float) center_point_x, (float) (center_point_y + center_point_x * 3 / 4), dotGridPaint);
        canvas.drawLine((float) center_point_x / 4, (float) center_point_y, (float) center_point_x * 7 / 4, (float) center_point_y, dotGridPaint);


        if (showMeters) {

            // Draw Elevation degree
            canvas.drawText("5m", (float) center_point_x + TypedValueCalculate.dp2pixel(2, mContext), (float) (center_point_y - center_point_x * 3 / 4 - TypedValueCalculate.dp2pixel(5, mContext)), textPaint);
            canvas.drawText("3m", (float) center_point_x + TypedValueCalculate.dp2pixel(2, mContext), (float) (center_point_y - center_point_x * 2 / 4 + TypedValueCalculate.dp2pixel(5, mContext)), textPaint);
            canvas.drawText("1m", (float) center_point_x + TypedValueCalculate.dp2pixel(2, mContext), (float) (center_point_y - center_point_x * 1 / 4 + TypedValueCalculate.dp2pixel(5, mContext)), textPaint);

        }


        for (int i = 0; i < nevList.size(); i++) {
            NEVData data = nevList.get(i);
            if (data.type == TYPE_BEFORE) {
                draw_EN_Error_to_XY_Coord(canvas, data.e, data.n, TypedValueCalculate.dp2pixel(4, mContext), basic_point_paint);
            } else if (data.type == TYPE_MODIFIED) {
                draw_EN_Error_to_XY_Coord(canvas, data.e, data.n, TypedValueCalculate.dp2pixel(4, mContext), other_point_paint);
            }
        }


    }

    private void draw_EN_Error_to_XY_Coord(
            Canvas canvas,
            double e_value_in_meters,
            double n_value_in_meters,
            int circle_size,
            Paint paint
    ) {
        double x = 0, y = 0;
        // calculate position
        // 5m = center_point_x*3/4
        x = center_point_x + e_value_in_meters * center_point_x * 3 / 4 / 5;
        y = center_point_y - n_value_in_meters * center_point_x * 3 / 4 / 5;

        // draw error
        canvas.drawCircle((float) x, (float) y, circle_size, paint);
    }


    public void drawErrorPoint(double n,
                               double e,
                               double v,
                               int type
    ) {
        newData = new NEVData(n, e, v, type);
        nevList.add(newData);
        invalidate();
    }

    public void showDegree(boolean show) {
        this.showMeters = show;
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

    public void setDotGridPaint(Paint paint) {
        dotGridPaint = paint;
        invalidate();
    }

    public void setBasic_point_paint(Paint paint) {
        basic_point_paint = paint;
        invalidate();
    }

    public void setOther_point_paint(Paint paint) {
        other_point_paint = paint;
        invalidate();
    }


}
