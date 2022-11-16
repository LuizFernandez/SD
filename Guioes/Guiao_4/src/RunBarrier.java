public class RunBarrier implements Runnable {

    private final Barrier b;

    public RunBarrier(Barrier b){
        this.b = b;
    }

    public void run(){
        int i = 0;
        try{
            int vezes = 4;
            while(i < vezes) {
                this.b.await();
                i++;
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
