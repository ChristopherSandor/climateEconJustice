package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String[] data = inputLine.split(",");
        String state = data[2];
        boolean check = false;

        StateNode pointer = firstState;
        while(pointer != null){
            if(pointer.getName().equals(state)){
                check = true;
            }
            pointer = pointer.getNext();
        }

        if(!check){
            StateNode newState = new StateNode(state, null, null);
            if(firstState == null){
                firstState = newState;
            } else{
                pointer = firstState;
                while(pointer.getNext() != null){
                    pointer = pointer.getNext();
                }
                pointer.setNext(newState);
            }
        }

    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] data = inputLine.split(",");
        String state = data[2];
        String county = data[1];

        // Cycles through all the states to find the state we want
        StateNode statePointer = firstState;
        while(!statePointer.getName().equals(state)){
            statePointer = statePointer.getNext();
        }

        // First determines if the county exists or not
        CountyNode countyPointer = statePointer.getDown();
        boolean countyCheck = false;
        while(countyPointer != null){
            if(countyPointer.getName().equals(county)){
                countyCheck = true;
            }
            countyPointer = countyPointer.getNext();
        }

        // Creates a new county if the county doesn't exist already
        if(!countyCheck){
            CountyNode newCounty = new CountyNode(county, null, null);
            if(statePointer.getDown() == null){
                statePointer.down = newCounty;
            } else{
                countyPointer = statePointer.getDown();
                while(countyPointer.getNext() != null){
                    countyPointer = countyPointer.getNext();
                }
                countyPointer.setNext(newCounty);
            }
        }

    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        String[] data = inputLine.split(",");
        String state = data[2];
        String county = data[1];
        String community = data[0];

        // Cycles through all the states to find the state we want
        StateNode statePointer = firstState;
        while(!statePointer.getName().equals(state)){
            statePointer = statePointer.getNext();
        }
        // Cycles through all the states to find the state we want
        CountyNode countyPointer = statePointer.getDown();
        while(!countyPointer.getName().equals(county)){
            countyPointer = countyPointer.getNext();
        }

        // First determines if the community exists or not
        CommunityNode communityPointer = countyPointer.getDown();
        boolean communityCheck = false;
        while(communityPointer != null){
            if(communityPointer.getName().equals(community)){
                communityCheck = true;
            }
            communityPointer = communityPointer.getNext();
        }

        // Creates a new community if the community doesn't exist already
        if(!communityCheck){
            // Need to create a data node to hold all the communities information:

            // Percent African American: index 3
            // Percent Native: index 4
            // Percent Asian: index 5
            // Percent White: index 8
            // Percent Hispanic: index 9
            // Disadvantaged: index 19 STRING
            // PM Level: index 49
            // Chance of Flood: index 37
            // Poverty Line: index 121

            double African_American = Double.parseDouble(data[3]);
            double Native = Double.parseDouble(data[4]);
            double Asian = Double.parseDouble(data[5]);
            double White = Double.parseDouble(data[8]);
            double Hispanic = Double.parseDouble(data[9]);
            String Disadvantaged = data[19];
            double PM_Level = Double.parseDouble(data[49]);
            double Chance_of_flood = Double.parseDouble(data[37]);
            double Poverty_Line = Double.parseDouble(data[121]);

            Data communitiesData = new Data(African_American, Native, Asian, White, Hispanic, Disadvantaged, PM_Level, Chance_of_flood, Poverty_Line);

            CommunityNode newCommunity = new CommunityNode(community, null, communitiesData);

            if(countyPointer.getDown() == null){
                countyPointer.down = newCommunity;
            } else{
                communityPointer = countyPointer.getDown();
                while(communityPointer.getNext() != null){
                    communityPointer = communityPointer.getNext();
                }
                communityPointer.setNext(newCommunity);
            }
        }

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        int disadvantagedCount = 0;

        StateNode statePointer = firstState;
        while(statePointer != null){

            CountyNode countyPointer = statePointer.getDown();
            while(countyPointer != null){

                CommunityNode communityPointer = countyPointer.getDown();
                while(communityPointer != null){

                    Data communityData = communityPointer.info;
                    double racePercent = 0.0;
                    String advantageStatus = communityData.getAdvantageStatus();

                    switch(race){
                        case "African American":
                            racePercent = (communityData.getPrcntAfricanAmerican() * 100);
                        break;

                        case "Native American":
                            racePercent = (communityData.getPrcntNative() * 100);
                        break;

                        case "Asian American":
                            racePercent = (communityData.getPrcntAsian() * 100);
                        break;

                        case "White American":
                            racePercent = (communityData.getPrcntWhite() * 100);
                        break;

                        case "Hispanic American":
                            racePercent = (communityData.getPrcntHispanic() * 100);
                        break;
                    }

                    if((racePercent >= userPrcntage) && (advantageStatus.equals("True"))){
                        disadvantagedCount++;
                    }

                    communityPointer = communityPointer.getNext();
                }

                countyPointer = countyPointer.getNext();
            }

            statePointer = statePointer.getNext();
        }


        return disadvantagedCount; 
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        int non_advantagedCount = 0;

        StateNode statePointer = firstState;
        while(statePointer != null){

            CountyNode countyPointer = statePointer.getDown();
            while(countyPointer != null){

                CommunityNode communityPointer = countyPointer.getDown();
                while(communityPointer != null){

                    Data communityData = communityPointer.info;
                    double racePercent = 0.0;
                    String advantageStatus = communityData.getAdvantageStatus();

                    switch(race){
                        case "African American":
                            racePercent = (communityData.getPrcntAfricanAmerican() * 100);
                        break;

                        case "Native American":
                            racePercent = (communityData.getPrcntNative() * 100);
                        break;

                        case "Asian American":
                            racePercent = (communityData.getPrcntAsian() * 100);
                        break;

                        case "White American":
                            racePercent = (communityData.getPrcntWhite() * 100);
                        break;

                        case "Hispanic American":
                            racePercent = (communityData.getPrcntHispanic() * 100);
                        break;
                    }

                    if((racePercent >= userPrcntage) && (advantageStatus.equals("False"))){
                        non_advantagedCount++;
                    }

                    communityPointer = communityPointer.getNext();
                }

                countyPointer = countyPointer.getNext();
            }

            statePointer = statePointer.getNext();
        }


        return non_advantagedCount; 
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList<StateNode> stateList = new ArrayList<StateNode>();

        StateNode statePointer = firstState;
        while(statePointer != null){

            boolean jailBreak = false;

            CountyNode countyPointer = statePointer.getDown();
            while(countyPointer != null){

                CommunityNode communityPointer = countyPointer.getDown();
                while(communityPointer != null){

                    Data communityData = communityPointer.info;

                    if(communityData.getPMlevel() >= PMlevel){
                        stateList.add(statePointer);
                        jailBreak = true;
                        break;
                    }

                    communityPointer = communityPointer.getNext();
                }

                if(jailBreak == true){
                    break;
                }

                countyPointer = countyPointer.getNext();
            }

            statePointer = statePointer.getNext();
            jailBreak = false;

        }


        return stateList; 
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {
        int chance_of_flood_count = 0;

        StateNode statePointer = firstState;
        while(statePointer != null){

            CountyNode countyPointer = statePointer.getDown();
            while(countyPointer != null){

                CommunityNode communityPointer = countyPointer.getDown();
                while(communityPointer != null){

                    Data communityData = communityPointer.info;

                    if((communityData.getChanceOfFlood()) >= userPercntage){
                        chance_of_flood_count++;
                    }

                    communityPointer = communityPointer.getNext();
                }

                countyPointer = countyPointer.getNext();
            }

            statePointer = statePointer.getNext();
        }

        return chance_of_flood_count; 
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        StateNode statePointer = firstState;
        while(!statePointer.getName().equals(stateName)){
            statePointer = statePointer.getNext();
        }

        ArrayList<CommunityNode> lowestIncomeCommunitiesList = new ArrayList<CommunityNode>();

        CountyNode countyPointer = statePointer.getDown();
        while(countyPointer != null){

            CommunityNode communityPointer = countyPointer.getDown();
            while(communityPointer != null){

                Data communityData = communityPointer.info;

                if(10 > lowestIncomeCommunitiesList.size()){
                    lowestIncomeCommunitiesList.add(communityPointer);
                } 
                else {
                    boolean replace_data = false;
                    for(CommunityNode list_item : lowestIncomeCommunitiesList){
                        if(communityData.getPercentPovertyLine() >= list_item.getInfo().getPercentPovertyLine()){
                            replace_data = true;
                            break;
                        }
                    }
                    if(replace_data == true){
                        CommunityNode lowest_community = lowestIncomeCommunitiesList.get(0);
                        int lowest_index = 0;
                        for(int index = 0; index < 10; index++){
                            if(lowest_community.getInfo().getPercentPovertyLine() > lowestIncomeCommunitiesList.get(index).getInfo().getPercentPovertyLine()){
                                lowest_community = lowestIncomeCommunitiesList.get(index);
                                lowest_index = index;
                            }
                        }

                        lowestIncomeCommunitiesList.set(lowest_index, communityPointer);
                    
                    }
                }

                communityPointer = communityPointer.getNext();
            }

            countyPointer = countyPointer.getNext();
        }
        
        return lowestIncomeCommunitiesList; 

    }
}

    
