import java.util.ArrayList;

/**
 * A class that represents and performs the functions of a CMDbProfile
 *
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class CMDbProfile {

    // Field that represents the profile's username
    private String userName;

    // Field that stores the profile's number of ratings
    private int numberOfRatings;

    // Field that stores the movies that this profile has rated
    private ArrayList<Movie> moviesArrayList;

    /**
     * A constructor that constructs a CMDbProfile with the given username.
     * Time Complexity: O(1)
     *
     * @param userName The name of this CMDbProfile
     */
    public CMDbProfile(String userName) {
        // Initialize the necessary fields
        this.userName = userName;
        this.numberOfRatings = 0;
        this.moviesArrayList = new ArrayList<Movie>();
    }

    /**
     * A method that changes this profile’s existing username to the specified new username.
     * Time Complexity: O(1)
     *
     * @param userName The specified new username
     */
    public void changeUserName(String userName) {
        this.userName = userName;
    }

    /**
     * A method that adds a rating for the specified movie to this CMDb profile. Assuming that the movie has not existed
     * in the database.
     * Time Complexity: O(log M) average case, O(M) worst case, where M is the number of movies in the database.
     *
     * @param movieName Name of the movie to be rated.
     * @param rating The rating of the movie, from 1 to 10.
     * @return True or False depending on whether the movie has been successfully rated.
     */
    public boolean rate(String movieName, int rating) {
        // Variable that stores the new movie to be rated and added to the database
        Movie newMovie = new Movie(movieName, rating);
        // Variable to check if the rating is valid
        boolean checkRatingValid;
        // If rating is not in the range from 1-10, set the variable to false
        if (rating < 1 || rating > 10) {
            checkRatingValid = false;
        }
        // Otherwise, sets the variable to true
        else {
            checkRatingValid = true;
        }
        // If rating is invalid, return false immediately
        if (checkRatingValid == false) {
            return false;
        }
        // A loop that loops through the movie list to find if there is any existing movie of the same name in the database
        for (Movie movie : this.moviesArrayList) {
            // If so, return false immediately
            if (movie.equals(newMovie)) {
                return false;
            }
        }
        // Now, set the rating for the movie, add the new movie to the database, and increment the number of ratings
        newMovie.setRatingOfMovie(rating);
        this.moviesArrayList.add(newMovie);
        this.numberOfRatings = this.numberOfRatings + 1;
        // Return true to indicate that the rating process has completed successfully
        return true;
    }

    /**
     * A method that changes the existing rating for the specified movie to the specified new rating for this CMDb profile.
     * Time Complexity: O(M) worst case, where M is the number of movies in the database.
     *
     * @param movie Any movie to be changed rating.
     * @param newRating The new rating for the movie.
     * @return True or False depending on whether the rating of the movie has been successfully changed.
     */
    public boolean changeRating(String movie, int newRating) {
        // Variable to store whether the rating has been successfully changed
        boolean ratingChanged = false;
        // Variable to check whether the new rating is valid
        boolean checkRatingValid;
        // If rating is not in the range from 1-10, set the variable to false
        if (newRating < 1 || newRating > 10) {
            checkRatingValid = false;
        }
        // Otherwise, sets the variable to true
        else {
            checkRatingValid = true;
        }
        // If rating is invalid, return false immediately
        if (checkRatingValid == false) {
            return false;
        }
        // A loop that loops through the movie list to find the existing movie of the same name in the database
        for (Movie tempMovie : this.moviesArrayList) {
            // Variable that stores the name of the current movie being iterated through
            String nameOfTempMovie = tempMovie.getNameOfMovie();
            // If the same movie is found, change its rating to the new rating
            if (nameOfTempMovie.equals(movie)) {
                tempMovie.setRatingOfMovie(newRating);
                // Indicate that the rating process has completed successfully and break the loop
                ratingChanged = true;
                break;
            }
        }
        // Indicate that the rating process has completed either successfully or unsuccessfully
        return ratingChanged;
    }

    /**
     * A method that removes the movie and its corresponding rating from the database.
     * If the specified movie does not exist in the database, return false. Otherwise, return true.
     * Time Complexity: O(M) worst case, where M is the number of movies in the database
     *
     * @param movie The movie to be deleted from the database.
     * @return If the specified movie does not exist in the database, return false. Otherwise, return true.
     */
    public boolean removeRating(String movie) {
        // Variable to store whether the rating has been successfully changed and whether the movie has been successfully removed
        boolean ratingAndMovieRemoved = false;
        // A loop that loops through the movie list to find the existing movie of the same name in the database
        for (Movie tempMovie : this.moviesArrayList) {
            // Variable that stores the name of the current movie being iterated through
            String nameOfTempMovie = tempMovie.getNameOfMovie();
            if (nameOfTempMovie.equals(movie)) {
                // Remove the movie from the database and decrement the number of ratings by 1
                this.moviesArrayList.remove(tempMovie);
                this.numberOfRatings = this.numberOfRatings - 1;
                // Indicate that the remove rating and movie process has completed successfully & break the loop
                ratingAndMovieRemoved = true;
                break;
            }
        }
        // Indicate that the rating process has completed either successfully or unsuccessfully by returning true or false
        if (ratingAndMovieRemoved == true) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * A method that returns all the user’s movies with the highest rating given by the user.
     * If this CMDb profile does not have any ratings yet, you should return null.
     * Time Complexity: O(1)
     *
     * @return A strings array containing all the user’s movies with the highest rating given by the user. Null if the
     * user has not had any ratings yet.
     */
    public String[] favorite() {
        // A temporary ArrayList to store the favorite movies of this profile
        ArrayList<Movie> favoriteMoviesList = new ArrayList<Movie>();
        // Variable to store the number's maximum (highest) rating
        int maximumRatingByUser = 0;
        // Variable to store the rating of the current movie being looped through
        int ratingOfCurrentMovie = 0;
        // If the number of ratings is 0, return null
        if (this.numberOfRatings == 0) {
            return null;
        }
        // Else, ignore it and let the method continue as normal
        else {
            ;
        }
        /* A loop that iterates through the movies array list to find if there is any movie with the rating higher than
         * the current max rating by the profile. */
        for (Movie tempMovie : this.moviesArrayList) {
            // Update the variable to store the rating of the current movie being looped through
            ratingOfCurrentMovie = tempMovie.getRatingOfMovie();
            // If the rating of the current movie is greater than the current highest rating, add it to the favorite movies list
            if (ratingOfCurrentMovie > maximumRatingByUser) {
                favoriteMoviesList.clear();
                favoriteMoviesList.add(tempMovie);
                // Sets the maximum rating variable accordingly
                maximumRatingByUser = tempMovie.getRatingOfMovie();
            }
            // If the rating of the current movie is smaller than the maximum rating by the user, do nothing and ignore it
            else if (ratingOfCurrentMovie < maximumRatingByUser) {
                ;
            }
            // If the rating of the current movie is equal to the maximum rating by the user, also add the movie to the list
            else if (ratingOfCurrentMovie == maximumRatingByUser) {
                favoriteMoviesList.add(tempMovie);
            }
        }
        // Variable to store the number of favorite movies currently in the list
        int numFavoriteMovies = favoriteMoviesList.size();
        // Variable to store the favorite movies to be returned later on
        String[] favoriteMoviesArray = new String[numFavoriteMovies];
        // Loops through the two arrays and lists to copy the items over and return the array of strings
        for (int i = 0; i < numFavoriteMovies; i = i + 1) {
            // Variable to store the current favorite movie in the favorite movies list
            Movie favoriteMovie = favoriteMoviesList.get(i);
            // Variable to store its respective name
            String nameOfFavorite = favoriteMovie.getNameOfMovie();
            // Copy its name over to the array
            favoriteMoviesArray[i] = nameOfFavorite;
        }
        // Return the above-mentioned array
        return favoriteMoviesArray;
    }

    /**
     * A method that returns the user’s k highest-rated movies.
     * Time Complexity: O(k log M), where M is the number of movies in the database.
     * Space Complexity: O(k)
     *
     * @param k The number to find the k highest-rated movies.
     * @return An array of the user's k highest-rated movies.
     */
    public String[] favorite(int k) {
        // If there is currently no ratings from the profile, return an empty array
        if (this.numberOfRatings == 0) {
            // an empty array to be returned
            String[] returnArray = new String[0];
            return returnArray;
        }
        // Variable to store the current favorite movies list
        ArrayList<String> favoriteMoviesList = new ArrayList<String>();
        // Variable to store the current movies list in the database
        ArrayList<Movie> currentMovieList = new ArrayList<Movie>(this.moviesArrayList);
        // Variable to store the current count of movies in the list
        int countOfMovies = 0;
        // Loops through the current movies list to find the k highest-rated movies
        while (countOfMovies != k && currentMovieList.isEmpty() == false) {
            // Create a variable to memorize the last-state index when looping
            int lastIndex = 0;
            // Loops through the current movies list to get the k highest-rated movies
            for (int index = 1; index < currentMovieList.size(); index = index + 1) {
                // Variable to store the current movie being looped through
                Movie currentMovie = currentMovieList.get(index);
                // Variable to store the current rating of the current movie
                int currentRating = currentMovie.getRatingOfMovie();
                // Variable to store the last movie at the last index
                Movie lastMovie = currentMovieList.get(lastIndex);
                // Variable to store the last rating of the last movie
                int lastMovieRating = lastMovie.getRatingOfMovie();
                // If the current movie's rating is greater than the last movie's rating, set the two variables be equal to each other
                if (currentRating > lastMovieRating) {
                    lastIndex = index;
                }
            }
            // Add the movies to the favorite movies list and remove the respective movie from the current movies list
            favoriteMoviesList.add(currentMovieList.get(lastIndex).getNameOfMovie());
            currentMovieList.remove(lastIndex);
            // Increment count of movies
            countOfMovies = countOfMovies + 1;
        }
        // At the end of the loop, create a new variable to store the favorite movies list as an array of strings then return it
        String[] returnArray = favoriteMoviesList.toArray(new String[0]);
        return returnArray;
    }

    /**
     * A method that returns a String containing the profile information for this CMDb profile.
     * Time Complexity: O(1)
     *
     * @return a String containing the profile information for this CMDb profile.
     */
    public String profileInformation() {
        // A StringBuilder to build the profile information for this CMDb profile.
        StringBuilder profileInformation = new StringBuilder();
        // Array to store the favorite movies array of this CMDb profile.
        String[] favoriteMoviesArray = favorite();
        // Variable to get one of the user's favorite movie.
        String favoriteMovie = new String();
        // If this user has no ratings, handle the case differently by stating that he/she currently has no ratings
        if (this.numberOfRatings == 0) {
            // Variable to store the profile information
            String profile = new String(this.userName + " (" + this.numberOfRatings + ")\nFavorite Movie: NO RATINGS YET!");
            // Append it to the StringBuilder instance and then return it
            profileInformation.append(profile);
            return profileInformation.toString();
        }
        if (favoriteMoviesArray.length != 0 && this.numberOfRatings != 0) {
            // Take a favorite movie from the favorite movies array
            favoriteMovie = favoriteMoviesArray[0];
            // Variable to store the profile information
            String profile = new String (this.userName + " (" + this.numberOfRatings + ")\nFavorite Movie: " + favoriteMovie);
            // Append it to the StringBuilder instance and then return it
            profileInformation.append(profile);
        }
        return profileInformation.toString();
    }

    /**
     * A method that returns true if this profile’s username is the same as the specified Object’s username and if it is also
     * a CMDb profile. Otherwise, return false.
     * Time complexity: O(A+B), where A is the number of characters in this profile’s username and B is the number of
     * characters in the specified profile’s username.
     *
     * @param o The object to be passed into this method
     * @return true if this profile’s username is the same as the specified Object’s username and if it is also
     * a CMDb profile. Otherwise, return false.
     */
    @Override
    public boolean equals(Object o) {
        // Variable to store the return state
        boolean returnValue = false;
        // If o is an instance of CMDbProfile, cast it to CMDbProfile and then check if its username is equal to this username
        if (o instanceof CMDbProfile) {
            // Cast o to CMDbProfile
            CMDbProfile cmDbProfile = (CMDbProfile) o;
            // If o's username is equal to this username, set return value to true
            if (this.userName.equals(cmDbProfile.userName)) {
                returnValue = true;
            }
            // Else, set it to false
            else {
                returnValue = false;
            }
        }
        // Otherwise, if o is not an instance of CMDbProfile, set it to false,
        else {
            returnValue = false;
        }
        // Return true or false depending on the above conditions
        return returnValue;
    }

    /**
     * A method that return this CMDbProfile's username.
     * Time Complexity: O(1)
     *
     * @return This CMDbProfile's username
     */
    protected String getUserName() {
        return this.userName;
    }

    /**
     * A method that checks whether the movie has been rated by a CMDbProfile.
     * Time Complexity: O(N) where N is the max number of movies in the list.
     *
     * @param movie The movie to check
     * @return True or false depending on whether the movie has been rated by a CMDbProfile.
     */
    protected boolean hasRated(String movie) {
        // Variable to store whether the movie has been rated by a CMDbProfile
        boolean hasRated = false;
        // Loops through the movies array list to see whether the movie has been rated
        for (Movie tempMovie : this.moviesArrayList) {
            // Variable to store the name of the current movie being looped through
            String nameOfTempMovie = tempMovie.getNameOfMovie();
            // If there is any matching movies, return true and break the loop
            if (nameOfTempMovie.equals(movie)) {
                hasRated = true;
                break;
            }
        }
        // Return true or false depending on whether the movie has been rated by a CMDbProfile.
        return hasRated;
    }

    /**
     * A class that stores and represents a Movie in the Case Movie Data Base
     *
     * @author David Nguyen
     * @since 04/02/2023
     * @version 1.0
     */
    private class Movie {

        // Field to store the name of the movie
        private String nameOfMovie;

        // Field to store the rating of the movie
        private int ratingOfMovie;

        /**
         * A constructor that creates a movie with the specified name and rating.
         * Time Complexity: O(1)
         *
         * @param nameOfMovie Specified name to create a movie with
         * @param ratingOfMovie Specified rating to create a movie with
         */
        protected Movie(String nameOfMovie, int ratingOfMovie) {
            // Initialize the necessary variable
            this.nameOfMovie = nameOfMovie;
            this.ratingOfMovie = ratingOfMovie;
        }

        /**
         * A method that returns the name of the movie.
         * Time Complexity: O(1)
         *
         * @return The name of this movie
         */
        protected String getNameOfMovie() {
            return this.nameOfMovie;
        }

        /**
         * A method that returns the rating of the movie.
         * Time Complexity: O(1)
         *
         * @return The rating of this movie
         */
        protected int getRatingOfMovie() {
            return this.ratingOfMovie;
        }

        /**
         * A method that sets the rating of the movie.
         * Time Complexity: O(1)
         *
         * @param ratingOfMovie The rating of this movie
         */
        protected void setRatingOfMovie(int ratingOfMovie) {
            this.ratingOfMovie = ratingOfMovie;
        }

    }

}