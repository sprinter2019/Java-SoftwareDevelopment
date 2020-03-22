## Design Discussion


### Design 1 by Baihan Lin

![design 1](../Design-Individual/blin300/design.png)

As shown in the UML class diagram above, this design adopts an intuitive visual layout. The hierarchy of the class layout is easy to follow, as the operations from the top level is clearly separable from the downstream operations. However, there are several cons for this design as well. For instance, the class relationships are missing. To improve, cardinality and direction should be listed in the relationships between classes. It was also pointed out that there should be another class for the pool of remaining letters in the wordplay game. One unmet requirement is that the statistics should include the game setting. Finally, the statistics class is likely unnecessary and hard to maintain. As part of the discussion, we also debate on whether the data structure should be language specific or UML generic. We finally agree that as Java is our selected language, Java-specific data structures can make the group UML diagram easier to follow.


### Design 2 by Joshua Hunsberger

![design 2](../Design-Individual/jhunsberger3/design.png)

As shown in the UML class diagram above, this design has a clear separation of all functionalities. `Driver` serves a role of main function where all other operations are aggregated to it. We agree that creating the class `GameSetting` as a separate class is very helpful because it is associated to both the `Game` and `GameStatistics` classes. There are also several cons for this design as well. We debated on whether to use aggregation vs dependency for the relationship between the classes Game and LetterPool, as well as `Rack` and `Board`. The words on the relationship lines should also be replaced with connection-specific wording. We also noted the missing cardinality from both sides of relationships, which we can improve by explicitly specifying in our group design.

### Design 3 by Md Khaled Hassan

![design 3](../Design-Individual/mhassan49/design.png)

The next design we reviewed addressed some of the items that were missing from the above-mentioned diagrams. One improvement in this design was that the `WordGame` class contains a time property as a `Date` object that will be useful for sorting the games played when viewing the history through game statistics. The data type displayed at the bottom of the diagram for `Date` captures how this time will be represented for each game, likely through the programming language-specific data type for dates. Additionally, we felt that the relationships that existed between the `PoolOfLetters`, `Board` and `Rack` were more explicitly diagrammed instead of assumed to be handled by the class representing the game. One improvement we discussed was that we thought that the cardinalities of the aggregation relationships should be explicitly written out and not assumed to be 1-to-1 if they were excluded. Another improvement we could make to this design was marking more of the attributes of the `WordGame` class as private and allowing getter or setter methods to control access to them.

### Design 4 by Mariyam Mohamed Ayoob

![design 4](../Design-Individual/mayoob3/design.png)

Finally, we discussed Mariyam's design submission. Her design simplified the class hierarchy used by the other designs in this group by collapsing separate classes for the Driver/UI/Menu and Game into a single `ScrabbleController` class.  Each of the aggregations shows a clear cardinality on both sides of the relationship.  Like the third design, this one also included a way to track a relative ordering of games played by way of the `gameId` property of the `ScrabbleController`.  Additionally, the design also simplified the statistics classes into a single method, `displayStatistics`, that accepts an enumeration to determine how it should display the appropriate information.
As an area for improvement, we discussed that the relationship between the `ScrabbleController` and the `Board`, `Rack` and `Pool` classes could either be represented as a dependency from the `ScrabbleController`, or the diamond symbol on the aggregation should be reversed.  Additionally, it was not obvious how the frequency of each `LetterTile` was tracked, so it was suggested that another property be added to the that class.

## Team Design

![team design](./design.png)

After reviewing each of our designs, the team decided to use Joshua's diagram as the basis for our team design.  We felt that it shared most of the classes, relationships and design elements we liked from the individual designs.

### Main Design Decisions

- Driver class

One thing we shared across all of our designs was a top-level class to handle the flow of control between the game and the various menus.  Whether we called a `Driver`, a `Menu` or a `Controller`, all of our designs shared this element. The `Driver` controls the settings, starts a game and records statistics.

- Game Settings

Josh’s and Khaled’s design shared this class and we decided to retain this because at any given time, it concisely represents the game settings. Another reason for having this class is that when the player edits the game setting when another game is in progress. This class will hold the setting for the next game until the current game is completed.

- Representing the Statistics

In representing the statistics classes, there are two strategies we considered. One is to represent them within the `Driver` as fields and update them with in-class methods as in Mariyam’s design. The other is to represent them as separate classes as in Baihan’s, Joshua’s and Khaled’s designs. We eventually decided to use the second strategy, because we believe it makes the diagram clearer and leaves flexibility for future developments (e.g. a change of requirement to include more statistics).

- Interplays Between `Rack`, `Board` and `LetterPool`

Mariyam’s and Khaled’s designs have association links among the three main classes in the game ( `Rack`, `Board` and `LetterPool`), while Baihan’s and Joshua’s have the three classes independently report to the top-level `Game` class. After the discussion, we decided that these in-between relationships are necessary, as the updates of these three classes are interwoven.

- Database Access Helper

We added this object to the diagram based on Mariyam’s design in order to have a dedicated class to have all the database-related methods.

- Updated Relationship types between `Game` and  `Board` classes and `Game` and `Rack` classes

The original design from Josh represented these relationships as aggregation. However, after discussion, we decided that dependency relationship is more appropriate for these classes.


### Addressing Cons:

To address the cons discussed in the first section, we started by adjusting the cardinalities on each side of the relationships between classes.  Additionally, we adjusted the connections between the `Game` class and each of the game component classes (`LetterPool`, `Rack`, `Board`) from aggregation relationships to dependencies.  To add some improvements that were included in other designs, we added a `gameId` property to the `Game` class to facilitate relative ordering of games when comparing statistics later.  We also added some additional relationships between the game component classes to better illustrate the flow of letters from the `LetterPool`, `Board` and `Rack`.


## Summary
1. For some of us, this is the first time we have designed an UML diagram. Group discussion with other teammates made it easy for us to understand various types relationships that are integral part of designing an UML diagram.
2. An UML diagram can be designed in many ways and the most important thing is to make sure that all the design requirements are conveyed concisely.
3. Collective team effort brings in more diversity and makes a design more efficient as well as simple at the same time.
4. Group discussion helps clearing out any misunderstanding regarding the requirements.
5. Even in the real-life software engineering process, selecting the most appropriate relationships, cardinalities and notations for the project UML diagrams is a challenging team effort. The group discussion simulated such a process and taught us how to go through the requirements together and converge our different ideas into a universal design that is understandable and implementable for everyone in the project.
6. Comparing different designs is especially useful - the remarks reflect each other's understanding and fill in the blind spots that everyone of us might be missing during the design process.


