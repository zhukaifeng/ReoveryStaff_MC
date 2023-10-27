package com.dclee.recovery.view.check;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dclee.recovery.R;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.create_order.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class CheckImageAdapter extends RecyclerView.Adapter<CheckImageAdapter.UploadImageViewHolder> {
    private List<String> uploadedImages = new ArrayList<>();
    private List<String> uNetImages = new ArrayList<>();
    private int maxNum = 3;
    private FragmentActivity context;
    private LayoutInflater inflater;
    private ImagePicker imagePicker;
    private OnImageClickListener onImageClickListener;
    private final int PICK_IMAGE = 1;
    private RequestUtil mRequestUtil;
    private ArrayList<ImageItem> images = new ArrayList<>();

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public void setUploadedImages(List<String> uploadedImages) {
        this.uploadedImages = uploadedImages;
        notifyDataSetChanged();
    }

    public String getUploadImages() {
        String imgs = "";
        for (int i = 0; i < uNetImages.size(); i++) {
            if (i < (uNetImages.size() - 1)) {
                imgs = imgs + uNetImages.get(i)+",";
            } else {
                imgs = imgs + uNetImages.get(i);
            }

        }
        return imgs;
    }

    public ArrayList<ImageItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageItem> images) {
        this.images = images;
    }

    public CheckImageAdapter(FragmentActivity context, RequestUtil requestUtil) {
        this.context = context;
        mRequestUtil = requestUtil;
        inflater = LayoutInflater.from(context);
        initImagePicker();
    }

    @NonNull
    @Override
    public UploadImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UploadImageViewHolder(inflater.inflate(R.layout.item_check_image, parent, false));
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == PICK_IMAGE) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null) {
                    return;
                }
                RequestParams requestParams = new RequestParams();
                List<File> uploadedFiles = new ArrayList<>();
                requestParams.setMultipart(true);
                final List<String> localPaths = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    uploadedFiles.add(new File(images.get(i).path));
                    String path = images.get(i).path;
                    localPaths.add(path);
                    //requestParams.addBodyParameter("images[" + i + "]", new File(path));
                }
                requestParams.addBodyParameter("images", uploadedFiles);
                //requestParams.addBodyParameter("images", getFileMap(localPaths));
                final LoadingDialog loadingDialog = new LoadingDialog(context)
                        .setLoadingText("图片上传中...");
                loadingDialog.show();
                mRequestUtil.postList("s1/uploads", requestParams, String.class, new RequestUtil.OnRequestFinishListener<List<String>>() {
                    @Override
                    public void onRequestSuccess(List<String> result) {
                        uploadedImages.addAll(localPaths);
                        uNetImages.addAll(result);
                        notifyDataSetChanged();
                        loadingDialog.close();
                    }

                    @Override
                    public void onRequestFail(int errorCode, String desc) {
                        loadingDialog.close();
                    }
                });
            }
//            else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
//                //预览图片返回
//                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
//                    selImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
//                    if (selImageList != null) {
//                        images.clear();
//                        images.addAll(selImageList);
//                        notifyDataSetChanged();
//                    }
//                }
//            }
        }
    }

    private static Map<String, File> getFileMap(List<String> fileNames) {
        Map<String, File> fileMap = new HashMap<>();
        for (String fileName : fileNames) {
            File file = new File(fileName);
            fileMap.put(file.getName(), file);
        }

        return fileMap;
    }

    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void toSelectImages() {
        new RxPermissions(context).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            imagePicker.setSelectLimit(maxNum - uploadedImages.size());
                            ImagePicker imagePicker = ImagePicker.getInstance();
                            imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
                            imagePicker.setCrop(false);
                            imagePicker.setShowCamera(true);  //显示拍照按钮
                            Intent intent = new Intent(context, ImageGridActivity.class);
                            context.startActivityForResult(intent, PICK_IMAGE);
                        } else {
                            ToastUtil.showToast(context, "用户无权限");
                        }
                    }
                });
    }

    @Override
    public void onBindViewHolder(@NonNull UploadImageViewHolder holder, final int position) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.selectedImage.getLayoutParams();
        if (position == getItemCount() - 1 && uploadedImages.size() != maxNum) {
            layoutParams.width = DensityUtil.dip2px(35);
            holder.selectedImage.setImageResource(R.mipmap.btn_add);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(true, null, position);
                    }
                    toSelectImages();
                }
            });
        } else {
            Glide.with(context).load(uploadedImages.get(position)).into(holder.selectedImage);
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(false, uploadedImages.get(position), position);
                    }
                }
            });
        }
        holder.selectedImage.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return uploadedImages.size() == maxNum ? uploadedImages.size() : uploadedImages.size() + 1;
    }

    public class UploadImageViewHolder extends RecyclerView.ViewHolder {
        ImageView selectedImage;

        public UploadImageViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedImage = itemView.findViewById(R.id.selected_image);
        }
    }

    public interface OnImageClickListener {
        void onImageClick(boolean isAddImage, String image, int position);
    }
}
