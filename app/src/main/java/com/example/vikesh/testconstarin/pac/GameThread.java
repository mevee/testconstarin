package com.example.vikesh.testconstarin.pac;

class GameThread extends Thread {
        private boolean mRun = false;

        private AnimateState state;
        private Context context;
        private Handler handler;
        private View surfaceHolder;
        private Paint paint;
        Fireworks fireworks;

        GameThread(View surfaceHolder ,Context context, Handler handler) {

            this.context = context;
            this.handler = handler;

            fireworks = new Fireworks(getWidth(), getHeight());

            paint = new Paint();
            paint.setStrokeWidth(2 / getResources().getDisplayMetrics().density);
            paint.setColor(80);

            paint.setAntiAlias(true);
        }

        public void doStart() {
            synchronized (surfaceHolder) {
                setState(AnimateState.asRunning);
            }
        }

        public void pause() {
            synchronized (surfaceHolder) {
                if (state == AnimateState.asRunning)
                    setState(AnimateState.asPause);
            }
        }

        public void unpause() {
            setState(AnimateState.asRunning);
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = surfaceHolder.(null);

                    synchronized (surfaceHolder) {
                        if (state == AnimateState.asRunning)
                            doDraw(c);
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setRunning(boolean b) {
            mRun = b;
        }

        public void setState(AnimateState state) {
            synchronized (surfaceHolder) {
                this.state = state;
            }
        }

        public void doDraw(Canvas canvas) {
            fireworks.doDraw(canvas, paint);
        }

        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {
                fireworks.reshape(width, height);
            }
        }
    }
