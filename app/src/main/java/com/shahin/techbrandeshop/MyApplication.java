package com.shahin.techbrandeshop;

import android.app.Application;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.shahin.techbrandeshop.dao.ProductDAO;
import com.shahin.techbrandeshop.database.AppDatabase;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.util.LocalDataManager;

import java.util.List;

public class MyApplication extends Application {

    public static final String CHANNEL_ID = "LT_Channel_ID";
    public static final String CHANNEL_NAME = "Laptop_Notification_Channel";

    @Override
    public void onCreate() {
        super.onCreate();
        LocalDataManager.init(getApplicationContext());
        if (!LocalDataManager.getFirstTimeInstall()) {
            LocalDataManager.setFirstTimeInstall(true);
            initDatabase();
        }
    }

    private void initDatabase() {
        ProductDAO productDAO = AppDatabase.getInstance(getApplicationContext())
                .productDAO();

        productDAO.delete();

        String arr = "[" +
                "{\"brand\":\"MSI\",\"description\":\"The MSI 15.6\\\" GE Series GE66 Raider Dragonshield Gaming Laptop combines both performance and aesthetics for gamers who want bit of flair. The bottom edge of the palm rest and its keys support customizable RGB lighting, which users can set to fit their style. This limited edition features the MSI Dragonshield logo and a sci-fi spaceship-themed design. Specs-wise, it\\u0027s equipped with a 2.4 GHz Intel Core i9-10980HK eight-core processor, 32GB of DDR4 RAM, a 1TB NVMe PCIe M.2 SSD, and an NVIDIA GeForce RTX 2070 SUPER graphics card. These enable the system to quickly load applications, multitask efficiently, and play graphically demanding games. MSI has also paired the hardware with a 1080p Full HD 300 Hz IPS display. Connectivity options include USB Type-A and Type-C, HDMI, Mini DisplayPort, SD media cards, Gigabit LAN, Wi-Fi 6, and Bluetooth 5.1. Other integrated features includes a webcam, microphones, speakers, and a combo audio jack. The operating system installed is Windows 10 Home.\",\"discount\":15.0,\"isFreeShipping\":true,\"price\":4.4999E7,\"productID\":0,\"productImages\":[\"R.drawable.laptop_1\",\"R.drawable.laptop_1_slide_1\",\"R.drawable.laptop_1_slide_2\",\"R.drawable.laptop_1_slide_3\",\"R.drawable.laptop_1_slide_4\"],\"productName\":\"Laptop MSI GE66 Raider 10SF 483VN Dragonshield Limited Edition\",\"quantityInStock\":4,\"rate\":5.0,\"specification\":{\"battery\":\"Li-Ion 4-Cell 99.9Wh\",\"connectionGate\":\"Killer ax Wi-Fi + Bluetooth v5.1\",\"cpu\":\"Intel® Core™ i7-10875H 2.30GHz - 5.10GHz 8 Cores 12 Threads\",\"gpu\":\"NVIDIA GeForce GTX 1650 4GB GDDR6 + Intel UHD Graphics\",\"hardDrive\":\"1 TB NVMe PCIe Gen3x4\",\"keyboard\":\"RGB Steel Series\",\"monitor\":\"15.6\\\" FHD (1920x1080)\",\"os\":\"Windows 11 SL 64 Bit\",\"ram\":\"16GB (8GB x2) DDR4 3200MHz Max 64GB\",\"weight\":2.38}},"
                +"{\"brand\":\"MSI\",\"description\":\"The MSI 15.6\\\" Creator Series Creator 15 Laptop is primarily designed for content creators; however, it's also a well-rounded system for everyday users and even gamers. Specs-wise, it's equipped with a 2.3 GHz Intel Core i7-10875H eight-core processor, 16GB of DDR4 memory, a 512GB NVMe PCIe M.2 SSD, and an NVIDIA GeForce RTX 2060 graphics card. Its 15.6\\\" IPS display features a 1920 x 1080 Full HD resolution and supports multi-touch inputs for enhanced efficiency and accessibility. Other integrated features include an SD card reader, USB Type-A ports, Thunderbolt 3, HDMI, Wi-Fi 6, Bluetooth 5.1, a backlit keyboard, a webcam, a microphone, speakers, and a combo audio jack. The operating system installed is Windows 10 Home.\",\"discount\":10,\"isFreeShipping\":true,\"price\":34999000,\"productID\":0,\"productImages\":[\"R.drawable.laptop_2\",\"R.drawable.laptop_2_slide_1\",\"R.drawable.laptop_2_slide_2\",\"R.drawable.laptop_2_slide_3\",\"R.drawable.laptop_2_slide_4\"],\"productName\":\"MSI 15.6\\\" Creator Series Creator 15 Multi-Touch\",\"quantityInStock\":4,\"rate\":5,\"specification\":{\"battery\":\"Li-Ion 4-Cell 99.9Wh\",\"connectionGate\":\"Wi-Fi 6 (802.11ax) + Bluetooth 5.1\",\"cpu\":\"Intel Core i7-10875H 2.3GHz-5.1GHz 8-Core\",\"gpu\":\"NVIDIA GeForce RTX 2060 with 6 GB GDDR6 VRAM\",\"hardDrive\":\"512 GB M.2 NVMe PCIe\",\"keyboard\":\"84-Key Notebook Backlight\",\"monitor\":\"LCD 15.6\\\" FHD (1920 x 1080) TouchScreen\",\"os\":\"Windows 11 SL 64 Bit\",\"ram\":\"16GB (8GB x2) DDR4 2666MH (maximum 64GB)\",\"weight\":2.1}},"
                + "{\"brand\":\"ASUS\",\"description\":\"Lorem ipsum dolor sit, amet consectetur adipisicing elit. Tempora officia natus, itaque consequuntur cum earum esse. Velit praesentium omnis sapiente numquam magni! Commodi, rerum! Qui, esse explicabo dolore architecto, distinctio molestiae excepturi a pariatur fugiat, repudiandae deserunt. Earum expedita illo provident minus dignissimos eius assumenda laudantium. Repellat, sunt. Dicta, harum,Lorem ipsum dolor sit, amet consectetur adipisicing elit. Tempora officia natus, itaque consequuntur cum earum esse. Velit praesentium omnis sapiente numquam magni! Commodi, rerum!\",\"discount\":12,\"isFreeShipping\":false,\"price\":19000000,\"productID\":0,\"productImages\":[\"R.drawable.laptop_8\",\"R.drawable.laptop_8_slide_1\",\"R.drawable.laptop_8_slide_2\",\"R.drawable.laptop_8_slide_3\",\"R.drawable.laptop_8_slide_4\"],\"productName\":\"Laptop ASUS Gaming FX506LH-HN002T\",\"quantityInStock\":15,\"rate\":4.5,\"specification\":{\"battery\":\"Lithium-Ion 3-Cell: 48 Wh\",\"connectionGate\":\"Wi-Fi 6 (802.11ax) + Bluetooth 5.1\",\"cpu\":\"AMD Ryzen 7 4800H 2.9GHz-4.2GHz 8-Core\",\"gpu\":\"NVIDIA GeForce GTX 1650 Ti with 4 GB GDDR6 VRAM\",\"hardDrive\":\"512 GB M.2 NVMe PCIe\",\"keyboard\":\"Notebook Keyboard with Backlight\",\"monitor\":\"IPS 15.6\\\" FHD (1920x1080)\",\"os\":\"Windows 10 Home\",\"ram\":\"16GB (8GB x2) DDR4 3200MHz (maximum 32GB)\",\"weight\":2.3}}"
                + "]";

        List<Product> products = new Gson().fromJson(arr,
                new TypeToken<List<Product>>(){}.getType());

        productDAO.addProduct(products.get(0), products.get(1), products.get(2));
    }
}