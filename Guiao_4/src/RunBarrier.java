public class RunBarrier implements Runnable {

    private Barrier b;

    public RunBarrier(Barrier b) {
        this.b =b;
    }

    public void run(){
        try{
            this.b.await();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}
