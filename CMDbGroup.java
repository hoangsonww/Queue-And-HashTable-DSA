import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A class that represents and performs the functions of a CMDbGroup.
 *
 * @author David Nguyen
 * @since 04/02/2023
 * @version 1.0
 */
public class CMDbGroup {

    // A static Hash Table that keep track of all registered users on CMDbProfiles
    private static HashTable<CMDbProfile> allRegisteredUsers = new HashTable<CMDbProfile>();

    // An Array List that keeps track of all group members of this CMDbGroup
    private ArrayList<CMDbProfile> membersOfThisGroup;

    private String[] groupMembersNames;

    /**
     * A constructor that creates a new empty CMDb group.
     * Time Complexity: O(1) worst case
     *
     */
    public CMDbGroup() {
        this.membersOfThisGroup = new ArrayList<CMDbProfile>();
        this.groupMembersNames = new String[0];
        if (allRegisteredUsers == null) {
            this.allRegisteredUsers = new HashTable<CMDbProfile>();
        }
    }

    /**
     * A method that returns a HashTable containing all CMDb user profiles that have registered for some group in the past.
     * @return A HashTable containing all CMDb user profiles that have registered for some group in the past.
     *
     */
    public static HashTable<CMDbProfile> registeredUsers() {
        return allRegisteredUsers;
    }

    /**
     * A method that returns an array containing all the usernames of the group members.
     * Time Complexity: O(1) worst case
     *
     * @return An array containing all the usernames of the group members.
     */
    public String[] group() {
        return this.groupMembersNames;
    }

    /**
     * A method that adds the specified member to this CMDb group.
     * Time Complexity: O(1) average case, O(N) worst case, where N is the number of members already in the group.
     *
     * @param member The member to be added.
     */
    public void addMember(CMDbProfile member) {
        // Checks if the member has already existed in this group
        boolean memberExists = this.membersOfThisGroup.contains(member);
        // If no, add them to the group as well as to the list of registered users
        if (memberExists == false) {
            this.membersOfThisGroup.add(member);
            this.allRegisteredUsers.put(member.getUserName(), member);
        }
        // Otherwise, stops the method
        else {
            ;
        }
        /* Updates the groupMembersNames array to store the correct, current members' names. Please note that this will
         * still achieve a runtime of O(1) average case and O(N) worst case, where N is the number of members already in
         * the group because this operation only occurs in the worst cases when the array needs to be resized and is not
         * performed every time group() is called. Therefore, I would argue that this entire method will still have O(1)
         * average time and O(N) worst time, where N is the number of members already in the group.
         */
        // New variable to store the current group members' names with its size one
        String[] newGroupMembersNamesArray = new String[this.groupMembersNames.length + 1];
        // Copies the group members' names from the old array to this new array
        System.arraycopy(this.groupMembersNames, 0, newGroupMembersNamesArray, 0, this.groupMembersNames.length);
        // Adds the new member's name to the new array at the last position
        newGroupMembersNamesArray[groupMembersNames.length] = member.getUserName();
        // Updates the groupMembersNames array field to store the correct, current members' names
        this.groupMembersNames = newGroupMembersNamesArray;
    }

    /**
     * A method that adds the specified members to this CMDb group.
     * Time Complexity: O(P) average case, O(P*N) worst case, where P is the length of the specified array and N is the
     * number of members already in the group.
     *
     * @param members The members to be added to this group.
     */
    public void addMember(CMDbProfile[] members) {
        // Loops through the members in the given array and add them to the group
        for (CMDbProfile everyMember : members) {
            addMember(everyMember);
        }
    }

    /**
     * A method that returns the specified user’s favorite movie.
     * If the user corresponding to the specification has not been registered, return null.
     * Time Complexity: O(1) average case, O(R) worst case, where R is the number of registered CMDbGroup users.
     *
     * @param userName The username of the profile to check the favorite with.
     * @return The user's favorite movie. NULL if the given username is invalid or if it does not exist in the database
     */
    public static String favorite(String userName) {
        // Variable to store the favorite movie's name from this user
        String favorite = new String();
        // If the given userName given is invalid, return NULL
        if (userName == null) {
            return null;
        }
        // If the given userName given is invalid, return NULL
        if (userName.length() == 0 || userName.isEmpty()) {
            return null;
        }
        // Tries to see if the user exists in the database as well as gets their favorite movie and then returns it
        try {
            // Variable to store the profile of the given username
            CMDbProfile userWithUserName = registeredUsers().get(userName);
            // Gets his/her favorite, then return it
            String[] userFavorites = userWithUserName.favorite();
            favorite = userFavorites[0];
            return favorite;
        }
        // If a NoSuchElementException is caught, it means that the user has not existed in the database. Return null.
        catch (NoSuchElementException exception) {
            return null;
        }
    }

    /**
     * A method that returns the movies which are the favorite of the most people in the group.
     * Time Complexity: O(N*M) average case, where N is the number of members in the group and M is the number of rated
     * movies the group member with the most ratings has. Worst case runtime: O(N^2 * M).
     *
     * @return Returns the movies which are the favorite of the most people in the group.
     */
    public String[] groupFavorites() {
        // Variable to store the list of favorite movies from the group
        ArrayList<String> favoriteMovies = new ArrayList<String>();
        // Variable that stores the maximum count of ratings of the movies
        int maximumRatingCountOfMovie = 0;
        // Loops through the members list to find the favorite movies
        for (CMDbProfile member : this.membersOfThisGroup) {
            // Variable to store this member (member being looped through)'s favorite
            String[] memberFavorites = member.favorite();
            // If this member is not null, let the method continue
            if (memberFavorites != null) {
                // Loops through the movie in this member's favorites list to find the most favorite movies
                for (String movie : memberFavorites) {
                    // Stores the count of favorite movies of this member
                    int currentMovieRatingCount = 0;
                    // Loops through the members of this group to find the most favorite movies
                    for (CMDbProfile otherMember : this.membersOfThisGroup) {
                        /* If the movie is deemed to be the most favorite and has not yet been rated by any member,
                         * increment current count of movie. */
                        if (otherMember != member && otherMember.hasRated(movie)) {
                            currentMovieRatingCount++;
                        }
                        else {
                            ;
                        }
                    }
                    // If the current count is greater than the maximum count, add it as it is a favorite movie
                    if (currentMovieRatingCount > maximumRatingCountOfMovie) {
                        favoriteMovies.clear();
                        favoriteMovies.add(movie);
                        // Sets the two counts equal to each other
                        maximumRatingCountOfMovie = currentMovieRatingCount;
                    }
                    // Else if they're equal to each other, still add it to the favorites list
                    else if (currentMovieRatingCount == maximumRatingCountOfMovie && !favoriteMovies.contains(movie)) {
                        favoriteMovies.add(movie);
                    }
                    // else, ignore it and do nothing - let method continue
                    else {
                        ;
                    }
                }
            }
            // else, ignore it and do nothing - let method continue
            else {
                ;
            }
        }
        // Variable to store the favorite movies of the group
        String[] groupFavorites = favoriteMovies.toArray(new String[0]);
        // Return the favorite movies of the group
        return groupFavorites;
    }

    /**
     * A method that returns a random movie selected out of k possibilities.
     * Time Complexity: O(k log M) average case, where M is the number of rated movies the group member with the most ratings has
     *
     * @param k k possibilities to find a random movie with
     * @return a random movie selected out of k possibilities. If k is below 0, no movies can be found with it. Thus, return null.
     */
    public String randomMovie(int k) {
        // If k is below 0, no movies can be found with it. Thus, return null.
        if (k < 0) {
            return null;
        }
        // Else, let the method continue normally
        else {
            ;
        }
        // Variable to store the random movies list
        ArrayList<String> randomMoviesList = new ArrayList<String>();
        // Variable to store the maximum number of favorites for this group
        int maxFavorites = 0;
        // Loops through the members in this group and find their favorite. Then assign the variable to this current number.
        for (CMDbProfile member : membersOfThisGroup) {
            // Variable that stores the number of favorite movies for a member in the group
            int numFavorites = member.favorite().length;
            if (numFavorites > maxFavorites) {
                maxFavorites = numFavorites;
            }
        }
        // Loops through the first k members of this group to get their k favorite movies
        for (int index1 = 1; index1 <= k; index1 = index1 + 1) {
            // Variable that stores the random index to find a random movie
            int randomIndex = (int) (Math.random() * membersOfThisGroup.size());
            // If it's larger than the current members of this group's size, return NULL.
            if (randomIndex >= this.membersOfThisGroup.size()) {
                return null;
            }
            // Else, let the method continue normally
            else {
                ;
            }
            // Variable that stores the random member from this group
            CMDbProfile randomMember = this.membersOfThisGroup.get(randomIndex);
            // Variable that stores the second index to get a random movie from
            int index2 = randomIndex % k + 1;
            // Variable that stores the number of favorite movies
            int numFavorites = randomMember.favorite().length;
            // Variable that stores the temporary number to be added to the movies list
            int numToAdd = index2 * numFavorites;
            // Variable that stores the number to be added to the movies list
            int numToAdd2 = (int) Math.ceil(numToAdd / (double) k);
            // Variable that stores an array of k favorite movies of this random member
            String[] favoriteMovies = randomMember.favorite(numToAdd2);
            // Loops through each favorite movie in this member's favorite movies list to randomly add it to the random movies list
            for (String favoriteMovie : favoriteMovies) {
                // Variable to keep track of duplicated movies
                boolean isDuplicate = false;
                // Loops through each random movie in the random movies list to check for random duplicated movies
                for (String randomMovie : randomMoviesList) {
                    // If the two movies are equal to each other, set the above variable to true and break this loop
                    if (favoriteMovie.equals(randomMovie)) {
                        isDuplicate = true;
                        break;
                    }
                    // Else, let the method continue normally
                    else {
                        ;
                    }
                }
                // If there is no duplicated movies currently, add it to the random movies list
                if (isDuplicate == false) {
                    randomMoviesList.add(favoriteMovie);
                }
                // Else, let the method continue normally
                else {
                    ;
                }
            }
        }
        // If the random movies list is still empty, return NULL
        if (randomMoviesList.isEmpty()) {
            return null;
        }
        // Otherwise, return a random movie got from the list
        else {
            int randomMovieIndex = (int) (Math.random() * randomMoviesList.size());
            return randomMoviesList.get(randomMovieIndex);
        }
        // All these random measures thus helps us to ensure that we will be able to get the most random movie as possible
    }

}