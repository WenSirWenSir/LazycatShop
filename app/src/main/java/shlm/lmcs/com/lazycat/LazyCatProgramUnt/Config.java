package shlm.lmcs.com.lazycat.LazyCatProgramUnt;

public class Config {

    public static final String DEBUG = "LazyCatProgramUnt";

    /**
     * �����û����ݵ�ʱ��  �õ��Ĺ����û����ص�״̬�ͱ�ʶ��
     */
    public static class HttpMethodUserAction {
        public static final int SEND_VERIFICATION = 0;
        /**
         * �������
         */
        public static final String GET_USERVAL = "get_userval";//��ȡ�û���ȫ������
        public static final String GET_DEFAULT_ADDR = "get_userdefault_addr";//��ȡĬ�ϵ��ռ���ַ
        public static final String GET_ALLADDR = "get_alladdr";//��ȡ�û��������ռ���ַ
        public static final String UPDATE_USER_SEX = "update_user_sex";//�����û����Ա�
        public static final String INSERT_USER_ADDR = "insert_user_addr";//����һ���û��ĵ�ַ��Ϣ

        /**
         * ��ʶ����
         */
        public static final String KEY_ACTION = "action";
        public static final String KEY_USER = "user_md5";
        public static final String KEY_TOKEN = "user_token";
        public static final String KEY_STATUS = "status";//��ֵ ��ȡ״̬
        public static final String KEY_PHONE = "user_phone";
        public static final String KEY_CODE = "user_code";
        public static final String KEY_UPDATESEX = "update_sex";//���µ��û���ֵ��
        public static final String KEY_ADDR_NAME = "addr_name";//����
        public static final String KEY_ADDR_TEL = "tel";//�绰
        public static final String KEY_ADDR_ADDR = "addr";//��ַ
        public static final String KEY_ADDR_PHYSICS = "physics_addr";//�����ַ
        public static final String KEY_ADDR_IN = "addr_in";//��������
        public static final String KEY_ADDR_USER_SEX = "user_sex";//�Ա�
        public static final String KEY_ADDR_USER_YEAR = "user_year";//����
        public static final String KEY_ADDR_DEFAULT = "default";//�Ƿ�Ĭ�ϵ�ַ

        /**
         * ״̬��
         */
        public static final String STATUS_SENDOK = "0";
        public static final String STATUS_LOGINOK = "0";
        public static final String STATUS_NO_USER = "2";
        public static final String STATUS_TOKEN_ERROR = "1";//token����
        public static final String STATUS_LOGCODE_TOMUCH = "-2";//�������̫��
        public static final String STATUS_GETVALUES_NODATA = "2";//û���û���������Ϣ
        public static final String STATUS_GETVALUES_OK = "0";//��ȡ���
        public static final String STATUS_GETVALUES_ERROR = "1";//token ����
        public static final String STATUS_SELECT_SEX_OK = "0";//�����û����Ա�ɹ�
        public static final String STATUS_SELECT_SEX_ERROR = "1";//�����û����Ա�ʧ��
        public static final String CHECK_VERIFICATION = "1";
        public static final String STATUS_INSERT_ADDR_OK = "0";//�����û��ĵ�ַ�ɹ�
        public static final String STATUS_INSERT_ADDR_ERROR = "1";//����ʧ��
        public static final String STATUS_INSERT_ADDR_TOKEN_ERROR = "2";//Token ����
        public static final String STATUS_USER_NOADDRS = "1";//�û�û�е�ַ��Ϣ




        public static final int UPDATE_USER_VALUES = 1;
    }

    /**
     * http request use method
     */
    public static class HttpMethod {
        public static final String HTTP_GET = "0";//use get
        public static final String HTTP_POST = "1";//use post
    }

    /**
     * gets the value in the returned data
     */
    public static class HttpMethodRequestStatus {
        public static final String HTTP_REQUEST_STATUS = "status";//http get status
    }


    /**
     * Status of  sucessful registration
     */
    public static class LRUserRequestStatus {
        public static final String LRUSER_OK = "0";//login sucess
        public static final String LRUSER_ERROR = "1";//login error,account can't login
        public static final String LRUSER_TOMUCH_ERROR = "2";//Too many login errors
        public static final String LRUSER_ERRORCODE = "3";//login failed,Verification is error
    }

    /**
     * in the returned HttpRequest Json
     * Corresponding field
     */
    public static class JSON_USERPAGE {
        public static final String USER_NAME = "name";
        public static final String USER_PHNOE = "phone";
        public static final String USER_EROR_NUMBER = "err_number";
        public static final String USER_TOKEN = "token";
        public static final String USER_SEX = "sex";
        public static final String USER_BIRTHDATE = "birthdate";
        public static final String USER_CARDID = "carid";
        public static final String USER_CARDNAME = "carname";
        public static final String USER_CARDSTART = "cardstart";
        public static final String USER_CARDEND = "cardend";
        public static final String USER_CARDIN = "cardin";
        public static final String USER_LEFTCOMPANY_ID = "leftcompany_id";
        public static final String USRE_LOGIN_VERIFICATION = "login_verifction";
        public static final String USER_REG_TIME = "reg_time";
        public static final String USER_HEADIMG = "head_img";
        public static final String USER_ABOUT = "user_about";
        public static final String USER_SIGNATURE = "user_signature";
        public static final String USER_LAST_INCOORD = "last_incoord";//The user last logged
        // incoord,longitude in the former��the dimension in the end
        public static final String USER_EDUCATION = "education";//ѧ��
        public static final String USER_INTEREST = "interest";//����
        public static final String USER_UNIT = "unit";//��λ
        public static final String USER_ISBLACK_LIST = "black_list";//�û��Ƿ������  0 ��ʾ���� 1 ��ʾΪ������
        public static final String USER_PHONE_MD5 = "phone_md5";

    }


    /**
     * Returns the desired  service addrss
     * For example, send smsService,user login,get user data,update user data
     */
    public static class HTTP_ADDR {
        public static String SERVICE = "http://47.102.205.26/";

        /**
         * get the address of the interface to send SMS captha
         * <p>
         * <p>
         * this sms can Send a Verification code
         *
         * @return
         */
        public static String SendVerificationCodeAddr() {
            return HTTP_ADDR.SERVICE + "reg/register.php";
        }
        public static String PHOTO_SERVICE_ADDR = "http://120.79.63.36/photo";

        /**
         * ��ȡ�����֤��ĵ�ַ
         */
        public static String CheckVerificationAddr() {
            return HTTP_ADDR.SERVICE + "reg/register.php";
        }

        /**
         * ��ȡ�û�page�ĵ�ַ
         */
        public static String getUser_init() {
            return HTTP_ADDR.SERVICE + "user/user_init.php";
        }


        /**
         * Ҫ������û����Ա�
         *
         * @return
         */
        public static String updateSex() {
            return HTTP_ADDR.SERVICE + "user/user_init.php";

        }

        public static String getallAddr() {
            return HTTP_ADDR.SERVICE + "user/user_init.php";
        }
    }

    /**
     * Use to select the template to send a SMS message
     */
    public static class SMS {

        public static final String SEND_SMS_OK = "0000";
        /**
         * Decide what type you want
         */
        public static final String USE_MODE_1 = "";
        public static final String USE_MODE_2 = "";
    }


    /**
     * the values use to get the data for android window
     */
    public static class Windows {


        public static final int GET_WINDOW_HEIGHT = 1;
        public static final int GET_WINDOW_WIDHT = 2;

    }

}
