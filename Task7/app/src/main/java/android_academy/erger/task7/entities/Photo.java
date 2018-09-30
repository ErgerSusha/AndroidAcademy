package android_academy.erger.task7.entities;

public class Photo {
    private int id;
    private PhotoUrls urls;

    public int getId() {
        return id;
    }

    public PhotoUrls getUrls() {
        return urls;
    }

    public static class PhotoUrls {
        private String thumb;
        private String small;
        private String regular;

        public String getThumb() {
            return thumb;
        }

        public String getSmall() {
            return small;
        }

        public String getRegular() {
            return regular;
        }
    }
}
