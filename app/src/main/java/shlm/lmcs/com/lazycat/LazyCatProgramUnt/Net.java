package shlm.lmcs.com.lazycat.LazyCatProgramUnt;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.LOAD_IMAGEPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ImageCache;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;

/**
 * �������ģ��  �й���������ʵļ������ύ�¼���������
 * <p>
 * ��ģ���������Զ��������������ҹ�˾��������ģ�� �κο��������android project������ʹ��
 * ����ģ��
 */
@SuppressLint("LongLogTag")
public class Net {
    private String tUrl = "";
    private StringBuffer mKvsBuffer = new StringBuffer();
    private Net.onVisitInterServiceListener mOnVisitInterServiceListener;
    private ImageCache imageCache = new ImageCache();
    private int VisitInterMethod;

    /**
     * ��GET��ʽ��ȡ������Ϣ
     *
     * @param tUrl                         ��ַ
     * @param mOnVisitInterServiceListener �����ص�
     * @param kvs                          ������,û�о�ֱ����NULL
     */
    public static void doGet(Context context, String tUrl, final Net.onVisitInterServiceListener
            mOnVisitInterServiceListener, String... kvs) {
        if (!Tools.isIntentConnect(context)) {
            //���������� �Ͳ���ʲô������
            if (mOnVisitInterServiceListener != null) {
                mOnVisitInterServiceListener.onNotConnect();
            }
            return;
        } else {
            final StringBuffer kvsBuffer = new StringBuffer();
            if (kvs != null && kvs.length > 1) {
                try {
                    for (int i = 0; i < kvs.length; i += 2) {
                        kvsBuffer.append(kvs[i] + "=" + kvs[i + 1] + "&");
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "SystemVisitInerService.java[+]:" + e.getMessage());
                }
            }
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... urls) {
                    try {
                        HttpURLConnection con = (HttpURLConnection) new URL(urls[0].trim()
                                .toString() + "?" + kvsBuffer.toString()).openConnection();
                        Log.i(Config.DEBUG, "���������ַ:" + urls[0].trim().toString() + "?" +
                                kvsBuffer.toString());
                        con.setRequestMethod("GET");
                        con.setConnectTimeout(5000);
                        con.setReadTimeout(5000);
                        con.connect();
                        if (con != null) {
                            InputStream is = con.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new
                                    InputStreamReader(is));
                            StringBuffer sb = new StringBuffer();
                            String ReadLine = "";
                            while ((ReadLine = bufferedReader.readLine()) != null) {
                                sb.append(ReadLine);
                            }
                            is.close();
                            con.disconnect();
                            bufferedReader.close();
                            return sb.toString();
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        Log.e(Config.DEBUG, "SystemVisitInerService.java[+]:" + e.getMessage());

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    if (s != null) {
                        if (mOnVisitInterServiceListener != null) {
                            mOnVisitInterServiceListener.onSucess(s);
                        }
                    } else {
                        if (mOnVisitInterServiceListener != null) {
                            mOnVisitInterServiceListener.onFail("");
                        }
                    }
                    super.onPostExecute(s);
                }
            }.execute(tUrl);
        }
    }

    public interface onVisitInterServiceListener {

        void onSucess(String tOrgin);//�ɹ��ļ���

        void onNotConnect();//����Ͽ�����

        void onFail(String tOrgin);//ʧ�ܵļ���
    }


    /**
     * ��ȡXML�ļ����������
     */
    @SuppressLint("StaticFieldLeak")
    public static void doGetXml(Context mContext, String url, final ProgramInterface
            .XMLDomServiceInterface xmlDomServiceInterface, String... kvs) {

        /**
         * �ж��Ƿ�û���������
         */
        if (!Tools.isIntentConnect(mContext)) {
            if (xmlDomServiceInterface != null) {
                xmlDomServiceInterface.onNotService();
            }
            return;
        } else {
            final StringBuffer kvsBuffer = new StringBuffer();
            if (kvs != null && kvs.length > 1) {
                try {
                    for (int i = 0; i < kvs.length; i += 2) {
                        kvsBuffer.append(kvs[i] + "=" + kvs[i + 1] + "&");
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "Net.java[+]:" + e.getMessage());
                }
            }
            /**
             * ����XMLinputStream
             */
            new AsyncTask<String, Void, InputStream>() {
                boolean isJson = false;//�ж��Ƿ����JSON����
                String origin = "";//json����������

                @Override
                protected InputStream doInBackground(String... urls) {
                    InputStream in = null;
                    try {
                        URL url = new URL(urls[0].trim().toString() + "?" + kvsBuffer.toString());
                        Log.i(Config.DEBUG, "������ʵĵ�ַ" + urls[0].trim().toString() + "?" +
                                kvsBuffer.toString());
                        if (url != null) {
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setConnectTimeout(2000);
                            con.setDoInput(true);
                            con.setRequestMethod("GET");
                            if (200 == con.getResponseCode()) {
                                //�ɹ�
                                Map<String, List<String>> headFiels = con.getHeaderFields();
                                int size = headFiels.size();
                                for (int i = 0; i < size; i++) {
                                    if (con.getHeaderFieldKey(i).equals("Content-Type")) {
                                        if (con.getHeaderField(i).indexOf("text/html") != -1) {
                                            Log.e(Config.DEBUG, "Ӧ��Ҫ��json����");
                                            isJson = true;
                                            BufferedReader bufferedReader = new BufferedReader
                                                    (new InputStreamReader(con.getInputStream()));
                                            String len;
                                            StringBuffer stringBuffer = new StringBuffer();
                                            while ((len = bufferedReader.readLine()) != null) {
                                                stringBuffer.append(len);
                                            }
                                            origin = stringBuffer.toString();
                                            Log.e(Config.DEBUG, "Json���ݷ���:" + origin);
                                        } else {
                                            Log.e(Config.DEBUG, "Ӧ��Ҫ��XML����");
                                            in = con.getInputStream();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(Config.DEBUG, "getXMLInterGet[+]" + e.getMessage());
                    }
                    return in;
                }

                @Override
                protected void onPostExecute(InputStream inputStream) {
                    if (inputStream != null) {
                        //�ɹ�
                        if (xmlDomServiceInterface != null) {
                            xmlDomServiceInterface.onSucess(inputStream);
                        }
                    } else {
                        //Ϊ��  ˵�����������ҪJSON����
                        if (xmlDomServiceInterface != null && isJson) {
                            if (!TextUtils.isEmpty(origin)) {
                                xmlDomServiceInterface.onJson(origin);
                            } else {
                                Log.e(Config.DEBUG, "Net.java[+]��ʹ��json���� ���ǻ�ȡ�ı�����ʧ��");
                            }

                        }
                    }
                    super.onPostExecute(inputStream);
                }
            }.execute(url);
        }
    }

    public static void doPostXml(final Context mContext, final StringBuilder xml, String url,
                                 final ProgramInterface programInterface) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... _url) {
                String _data = null;
                //����xml������Ϣ
                //StringBuilder xml = new StringBuilder();
                //xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
                //xml.append("<body>");
                //xml.append("<addr_name>������</addr_name>");
                //xml.append("</body>");
                try {
                    byte[] xmlbyte = xml.toString().getBytes("UTF-8");
                    Log.i(Config.DEBUG, "�ύXML������Ϣ" + xml);
                    URL url = new URL(_url[0]);//��ַ
                    Log.i(Config.DEBUG, "�����ַ:" + url.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));
                    conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                    OutputStream outStream = conn.getOutputStream();
                    outStream.write(xmlbyte);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                                (is));
                        StringBuffer sb = new StringBuffer();
                        String ReadLine = "";
                        while ((ReadLine = bufferedReader.readLine()) != null) {
                            sb.append(ReadLine);
                        }
                        is.close();
                        conn.disconnect();
                        bufferedReader.close();
                        _data = sb.toString();
                    } else {
                        InputStream eis = conn.getErrorStream();
                        InputStreamReader isr = new InputStreamReader(eis, "UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        Log.i(Config.DEBUG, "Net.java[+]xml�ύʧ��");
                        String line;
                        while ((line = br.readLine()) != null) {
                            Log.e(Config.DEBUG, "Net.java[+]ErrorStream:" + line);
                        }
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "Net.java[+]" + e.getMessage() + "1");
                    e.printStackTrace();
                }
                return _data;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    Log.e(Config.DEBUG, "Net.java[+]xml�ύ���ص�����Ϊnull");
                    if (programInterface != null) {
                        programInterface.onFaile("", 0);
                    } else {
                        Log.e(Config.DEBUG, "Net.java[+]xml�ύ���ݻص�Ϊ��");
                    }
                } else {
                    //��ʼ��������
                    if (programInterface != null) {
                        programInterface.onSucess(s.toString(), 0);
                    } else {
                        Log.e(Config.DEBUG, "Net.java[+]xml�ύ���ݻص�Ϊ��");
                    }
                }
                super.onPostExecute(s);
            }
        }.execute(url);


    }

    public static void doGetimg(String imgfile, final ProgramInterface.doGetImg _doGetimg) {
        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... imgurl) {
                Bitmap bitmap = null;
                try {
                    URL url = new URL(imgurl[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(4000);
                    con.setRequestMethod("GET");
                    con.connect();
                    if (con.getResponseCode() == 200) {
                        bitmap = BitmapFactory.decodeStream(con.getInputStream());
                        Log.i(Config.DEBUG, "���ʳɹ�");
                    } else {
                        Log.e(Config.DEBUG, "����ʧ�� ���ʷ���״̬��:" + con.getResponseCode());
                    }
                } catch (MalformedURLException e) {
                    Log.e(Config.DEBUG, "Net.java[+]" + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(Config.DEBUG, "Net.java[+]" + e.getMessage());

                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    _doGetimg.onSucess(bitmap);
                } else {
                    _doGetimg.onFain();
                }
                super.onPostExecute(bitmap);
            }
        }.execute(Config.HTTP_ADDR.PHOTO_SERVICE_ADDR.trim() + imgfile.trim());


    }


    /**
     * ���߳�ִ������ͼƬ����Ĺ�����
     */
    public void doThreadimage(final LOAD_IMAGEPAGE load_imagepage) {
        Log.e(Config.DEBUG,"doThreadimage[url]" + load_imagepage.getImg_url());
        if (imageCache.getImage(load_imagepage.getTag()) != null) {
            //���ھͲ�Ҫ�ظ�������
            /*�ж�IMAGEView�Ƿ�Ϊ��*/
            if (load_imagepage.getImg().getDrawable() != null) {
                load_imagepage.getImg().setImageBitmap(imageCache.getImage(load_imagepage.getTag
                        ()));
            } else {
                /*û��ִ��Ҫ��*/
            }
        } else {
            doGetimg(load_imagepage.getImg_url(), new ProgramInterface.doGetImg() {
                @Override
                public void onSucess(Bitmap bitmap) {
                    if (imageCache.getImage(load_imagepage.getTag()) != null) {
                        /*����ͼƬ��Ϣ ������*/
                        if (load_imagepage.getImg().getDrawable() != null) {
                            load_imagepage.getImg().setImageBitmap(imageCache.getImage
                                    (load_imagepage.getTag()));
                        } else {
                            /*û��ִ��Ҫ��*/
                        }
                    } else {
                        imageCache.saveImage(load_imagepage.getTag(), bitmap);
                        load_imagepage.getImg().setImageBitmap(imageCache.getImage(load_imagepage
                                .getTag()));
                    }
                }

                @Override
                public void onFain() {

                }
            });
        }


    }


}
