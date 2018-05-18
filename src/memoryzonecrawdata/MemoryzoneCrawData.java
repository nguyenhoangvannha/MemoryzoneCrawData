/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoryzonecrawdata;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import memoryzonecrawdata.model.MemoryItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author naco
 */
public class MemoryzoneCrawData {

    static String link = "https://memoryzone.com.vn/san-pham/ssd";
    static ArrayList<String> rootLinks;
    static ArrayList<String> itemTypes;
    static String parentFolder;

    public static void main(String[] args) {
        itemTypes = new ArrayList<>();
        File file = new File(".");
        try {
            parentFolder = file.getCanonicalPath() + "\\data";
            file = new File(parentFolder);
            if (!file.exists()) {
                file.mkdir();
            }
            System.out.println("FOLDER:  " + parentFolder);
        } catch (IOException ex) {
            Logger.getLogger(MemoryzoneCrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
        getRootLinks();
        System.out.println(rootLinks.size());
        for (String rootLink : rootLinks) {
            downloadData(rootLink);
        }
        Gson gson = new Gson();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(parentFolder + "\\" + "index.json");
            gson.toJson(itemTypes, fileWriter);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MemoryzoneCrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void getRootLinks() {
        try {
            // TODO code application logic here
            Document document = Jsoup.connect(link).get();
            Elements elements = document.select(".ubermenu-tab > .ubermenu-target");
            rootLinks = new ArrayList<>();
            for (Element e : elements) {
                String href = e.attr("href");
                if (!href.equals("#") && !href.equals("https://memoryzone.com.vn/san-pham/best-seller")) {
                    rootLinks.add(href);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MemoryzoneCrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getImageName(String link) {
        String result = link.substring(link.lastIndexOf("/") + 1);
        return result;
    }

    private static void downloadData(String rootLink) {
        try {
            ArrayList<MemoryItem> memoryItems = new ArrayList<>();
            Document document = Jsoup.connect(rootLink).get();
            String title = document.select(".listing-title").text();
            itemTypes.add(title);
            //Download
            String saveImageLink = parentFolder + "\\" + title;
            File subFolder = new File(saveImageLink.replace("/", ""));
            if (!subFolder.exists()) {
                subFolder.mkdir();
            }
            System.out.println("Page: " + title);
            Elements elements = document.select(".products-entry");
            for (Element e : elements) {
                String name = e.select("h4 > a").text();
                System.out.println("\tItem: " + name);
                String link = e.select("h4 > a").attr("href");//ok
                String sprice = e.select(".woocommerce-Price-amount").text();
                String imageLink = e.select("a > img").attr("data-lazy-src");
                String image = title.replace("/", "") + "/" + getImageName(imageLink);
                int price = -1;
                try {
                    sprice = sprice.substring(0, sprice.indexOf('₫'));
                    sprice = sprice.replace(",", "");
                    price = Integer.parseInt(sprice.trim());
                } catch (Exception easa) {

                }
                MemoryItem item = new MemoryItem(name, price, image);
                getDescription(link, item);
                memoryItems.add(item);
                saveImageLink = parentFolder + "\\" + title.replace("/", "") + "\\" + getImageName(imageLink);
                downloadImage(imageLink, saveImageLink);
            }
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter(parentFolder + "\\" + title.replace("/", "") + ".json");
            gson.toJson(memoryItems, fileWriter);
            fileWriter.close();
        } catch (Exception ex) {
            Logger.getLogger(MemoryzoneCrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void getDescription(String link, MemoryItem item) {
        String des = "";
        Document document;
        try {
            document = Jsoup.connect(link).get();
            des = document.select("tbody").text();
            item.setMOTA(des);
            Element table = document.selectFirst(".specTable");
            Elements elements = table.select("tr");
            for (Element e : elements) {
                String nss = "Nhà sản xuất".toLowerCase();
                String model = "Model".toLowerCase();
                String cgt = "Chuẩn giao tiếp".toLowerCase();
                String dl = "Dung lượng".toLowerCase();
                String bh = "Bảo hành".toLowerCase();
                String s = e.text();
                String t = s.toLowerCase();
                String v = null;
                if (t.contains(nss)) {
                    v = s.substring(s.indexOf("uất") + 3).trim();
                    item.setNHASANXUAT(v);
                }
                if (t.contains(model)) {
                    v = s.substring(s.indexOf("odel") + 4).trim();
                    item.setMODEL(v);
                }
                if (t.contains(cgt)) {
                    v = s.substring(s.indexOf("iếp") + 3).trim();
                    item.setCHUANGIAOTIEP(v);
                }
                if (t.contains(dl)) {
                    v = s.substring(s.indexOf("ượng") + 4).trim();
                    item.setDUNGLUONG(v);
                }
                if (t.contains(bh)) {
                    v = s.substring(s.indexOf("ành") + 3).trim();
                    item.setBAOHANH(v);
                }
            }
            System.out.println("\t\tDownloaded: " + link);
        } catch (Exception ex) {
            Logger.getLogger(MemoryzoneCrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void downloadImage(String link, String saveLink) {
        URL url;
        try {
            url = new URL(link);
            URLConnection connection = url.openConnection();
            InputStream is = url.openStream();
            try {
                Files.copy(is, Paths.get(saveLink));
                System.out.println("Downloaded: " + link);
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            Logger.getLogger(MemoryzoneCrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
