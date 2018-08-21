
//byte和double的转换工具，
public class DoubleByteUtils {


    public  byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }
    public  double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    public  double[] bytes2DoubleArrays(byte[] arr){
        double []  result = new double[arr.length/8+1];
        byte [] materials = new byte[8];
        for (int group = 0; group<arr.length;group = group+8) {
            int resultIndex = 0;
            int index =0;
            for (int i = group; i<group+8&&i<arr.length; i++ ) {
                materials[index] = arr[i];
                index++;
            }
            index = 0;
            result [resultIndex] = bytes2Double(materials);
            resultIndex++;
        }
        return result;
    }

    public  byte[] doubles2bytes(double[] arr){
        byte [] result = new byte[arr.length*8];
        int index = 0;
        for (int i =0; i<arr.length; i++){
            byte [] temp = new byte[8];
            temp  = double2Bytes(arr[i]);
            for (int j = 0 ; j <temp.length;j++) {
                result[index] = temp[j];
                index++;
            }
        }
        return result;
    }

    public  void main(String[] args) {
        byte[] bytesTest = {11,22,33,44,55,66,77,88,88};
        double[] a = new double[8];
        a = bytes2DoubleArrays(bytesTest);
        for (double i : a){
            System.out.println("==========");
            System.out.println(i);
        }

        double[] doublesTest={503.222,504.222};

        byte[] kk = new byte[16];

        kk = doubles2bytes(doublesTest);
        for (byte k : kk){
            System.out.println("==========");
            System.out.println(k);
        }

    }
}
