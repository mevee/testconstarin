package com.example.vikesh.testconstarin;

public class ScreenShot extends AsyncTask<Void, Void, File> {

    private final WeakReference<Context> contextref;
    private final WeakReference<View> viewRef;
    private final int rank;
    private Bitmap bitmap;

    public ScreenShot(Context context, View view, int rank) {
        contextref = new WeakReference<Context>(context);
        viewRef = new WeakReference<View>(view);
        this.rank = rank;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        View view = viewRef.get();

        if (view != null) {
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(bitmap));
        }
    }

    @Override
    protected File doInBackground(Void... voids) {

        Context context = contextref.get();
        if (bitmap != null && context!=null) {
            try {

                File file = new File(context.getExternalCacheDir(), "game.png");
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                file.setReadable(true, false);
                shareRank(context, file, rank);
                return file;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(File aFile) {
        super.onPostExecute(aFile);
    }
}