import biz.source_code.dsp.filter.FilterPassType;
import biz.source_code.dsp.filter.IirFilterCoefficients;
import biz.source_code.dsp.filter.IirFilterDesignExstrom;

import java.util.Arrays;


//语音降噪滤波器，
public class MyFliter {

    public static synchronized double[] IIRFilter(double[] signal, double[] a, double[] b) {

        double[] in = new double[b.length];
        double[] out = new double[a.length-1];

        double[] outData = new double[signal.length];

        for (int i = 0; i < signal.length; i++) {

            System.arraycopy(in, 0, in, 1, in.length - 1);
            in[0] = signal[i];

            //calculate y based on a and b coefficients
            //and in and out.
            float y = 0;
            for(int j = 0 ; j < b.length ; j++){
                y += b[j] * in[j];

            }

            for(int j = 0;j < a.length-1;j++){
                y -= a[j+1] * out[j];
            }

            //shift the out array
            System.arraycopy(out, 0, out, 1, out.length - 1);
            out[0] = y;

            outData[i] = y;

        }
        return outData;
    }

    public static double[] useFliter( double[] valueA ){
        IirFilterCoefficients iirFilterCoefficients;
        iirFilterCoefficients = IirFilterDesignExstrom.design(FilterPassType.lowpass, 10,
                10.0 / 50, 10.0 / 50);
        for (int i=0;i<iirFilterCoefficients.a.length;i++) {
            System.out.println("A["+i+"]:"+iirFilterCoefficients.a[i]);
        }
        for (int i=0;i<iirFilterCoefficients.b.length;i++) {
            System.out.println("B["+i+"]:"+iirFilterCoefficients.b[i]);
        }

        valueA = IIRFilter(valueA, iirFilterCoefficients.a, iirFilterCoefficients.b);

        return valueA;
    }


        public static void main(String[] args) {

            double[] time = new double[150];
            double[] valueA = new double[150];
            for (int i = 0; i < 50 * 3; i++) {
                time[i] = i / 50.0;
                valueA[i] = Math.sin(2 * Math.PI * 5 * time[i])+Math.sin(2 * Math.PI * 15 * time[i]);
            }

            IirFilterCoefficients iirFilterCoefficients;
            iirFilterCoefficients = IirFilterDesignExstrom.design(FilterPassType.lowpass, 10,
                    10.0 / 50, 10.0 / 50);
            for (int i=0;i<iirFilterCoefficients.a.length;i++) {
                System.out.println("A["+i+"]:"+iirFilterCoefficients.a[i]);
            }
            for (int i=0;i<iirFilterCoefficients.b.length;i++) {
                System.out.println("B["+i+"]:"+iirFilterCoefficients.b[i]);
            }

            valueA = IIRFilter(valueA, iirFilterCoefficients.a, iirFilterCoefficients.b);

            Arrays.stream(valueA).forEach(value -> {
                System.out.println(value);
            });
        }
}
