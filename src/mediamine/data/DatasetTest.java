package mediamine.data;

import java.util.List;

import mediamine.model.MediaItem;

public class DatasetTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       MEDIAMINE DATA TEST");
        System.out.println("=================================");

        DatasetManager manager =
                new DatasetManager("dataset");

        System.out.println("\nLoading products...");

        List<String[]> products =
                manager.loadProducts();

        System.out.println(
                "Products loaded: " + products.size()
        );

        System.out.println("\nConverting products to MediaItems...");

        List<MediaItem> mediaItems =
                manager.loadMediaItems();

        System.out.println(
                "MediaItems created: " + mediaItems.size()
        );

        System.out.println("\nSample MediaItems:");

        int count = Math.min(5, mediaItems.size());

        for (int i = 0; i < count; i++) {

            MediaItem item = mediaItems.get(i);

            System.out.println(
                    (i + 1) + ". " + item
            );
        }

        System.out.println("\n=================================");
        System.out.println("       DATA TEST COMPLETE");
        System.out.println("=================================");
    }
}