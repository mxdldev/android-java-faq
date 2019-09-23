package com.mxdl.faq.jvm;

/**
 * Description: <FinalizeEscapeGC><br>
 * Author:      mxdl<br>
 * Date:        2019/9/20<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        SAVE_HOOK = new FinalizeEscapeGC();
        kill();
        //kill();

    }

    private static void kill() {
        //下面这段代码与上面的完全相同，但是这次自救会失败
        SAVE_HOOK = null;
        System.gc();
        //因为finalize方法的优先级很低，所以暂停0.5秒来等待它。
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (SAVE_HOOK != null) {
            System.out.println("Yes,i'm alive.");
        } else {
            System.out.println("no,i am dead.");
        }
    }

}
