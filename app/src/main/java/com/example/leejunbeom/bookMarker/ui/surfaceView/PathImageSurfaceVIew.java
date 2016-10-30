package com.example.leejunbeom.bookMarker.ui.surfaceView;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * Created by noduritoto on 2016. 10. 28..
 */

public class PathImageSurfaceVIew extends Surface implements SurfaceHolder.Callback {

    /**
     * Create Surface from a {@link SurfaceTexture}.
     * <p>
     * Images drawn to the Surface will be made available to the {@link
     * SurfaceTexture}, which can attach them to an OpenGL ES texture via {@link
     * SurfaceTexture#updateTexImage}.
     *
     * @param surfaceTexture The {@link SurfaceTexture} that is updated by this
     *                       Surface.
     * @throws OutOfResourcesException if the surface could not be created.
     */
    public PathImageSurfaceVIew(SurfaceTexture surfaceTexture) {
        super(surfaceTexture);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
