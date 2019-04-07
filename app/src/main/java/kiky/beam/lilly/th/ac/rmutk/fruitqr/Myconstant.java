package kiky.beam.lilly.th.ac.rmutk.fruitqr;

public class Myconstant {


    private String[] favoriteFruits = {"โปรดเลือกผลไม้", "ส้ม", "แตงโม", "ทุเรียน", "ขนุน"};
    private String[] units = {"กิโลกรัม", "ผล", "ลัง"};

    private String nameFileSharePreference = "Fruit";

    private String urlAddDetailFramer = "https://www.androidthai.in.th/rmutk/addDetailFramerMaster.php";
    private String urlAddUser = "https://www.androidthai.in.th/rmutk/addDataLilly.php";
    private String urlGetAllData = "https://www.androidthai.in.th/rmutk/getAllDatalilly.php";
    private String urlGetDataWhereQR = "https://www.androidthai.in.th/rmutk/getDetailWhereQRmaster.php";
    private String urlGetUserWhereId = "https://www.androidthai.in.th/rmutk/getUserWhereId.php";
    private String urlGetAllDetail = "https://www.androidthai.in.th/rmutk/getDetail.php";
    private String urlGetDetailWhereIdUser = "https://www.androidthai.in.th/rmutk/getDetailWhereIdUser.php";

    public String getUrlAddDetailFramer() {
        return urlAddDetailFramer;
    }

    public String[] getFavoriteFruits() {
        return favoriteFruits;
    }

    public String[] getUnits() {
        return units;
    }

    public String getNameFileSharePreference() {
        return nameFileSharePreference;
    }

    public String getUrlGetDetailWhereIdUser() {
        return urlGetDetailWhereIdUser;
    }

    public String getUrlGetAllDetail() {
        return urlGetAllDetail;
    }

    public String getUrlGetUserWhereId() {
        return urlGetUserWhereId;
    }

    public String getUrlGetDataWhereQR() {
        return urlGetDataWhereQR;
    }

    public String getUrlGetAllData() {
        return urlGetAllData;
    }

    public String getUrlAddUser() {
        return urlAddUser;
    }
}
