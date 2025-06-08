package com.javaoop.movie_booking_app.model;

public enum RatingCategory {
    普遍級 {
        @Override
        public Integer minimumAge() {
            return 0;
        }
    },
    保護級 {
        @Override
        public Integer minimumAge() {
            return 6;
        }
    },
    /**
     * For age 15+
     */
    輔導級 {
        @Override
        public Integer minimumAge() {
            return 15;
        }
    },
    限制級 {
        @Override
        public Integer minimumAge() {
            return 18;
        }
    };

    public abstract Integer minimumAge();
}
