package com.javaoop.movie_booking_app.model;

/**
 * Represents the rating category of a movie, which indicates the minimum age required
 * to view the movie.
 * <p>
 * Each category overrides the {@link #minimumAge()} method to specify the minimum
 * recommended viewer age.
 * </p>
 */
public enum RatingCategory {
    /**
     * 普遍級 (General Audience) - Suitable for all ages.
     * Minimum age: 0
     */
    普遍級 {
        @Override
        public Integer minimumAge() {
            return 0;
        }
    },

    /**
     * 保護級 (Parental Guidance) - Suitable for viewers aged 6 and above.
     * Minimum age: 6
     */
    保護級 {
        @Override
        public Integer minimumAge() {
            return 6;
        }
    },

    /**
     * 輔導級 (Guidance Recommended) - Suitable for viewers aged 15 and above.
     * Minimum age: 15
     */
    輔導級 {
        @Override
        public Integer minimumAge() {
            return 15;
        }
    },

    /**
     * 限制級 (Restricted) - Suitable for viewers aged 18 and above.
     * Minimum age: 18
     */
    限制級 {
        @Override
        public Integer minimumAge() {
            return 18;
        }
    };

    /**
     * Returns the minimum age required to view movies of this rating category.
     *
     * @return the minimum recommended viewer age
     */
    public abstract Integer minimumAge();
}
