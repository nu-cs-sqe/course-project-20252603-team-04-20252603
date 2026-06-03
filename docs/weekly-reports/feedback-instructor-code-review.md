# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 7-8 Code Review
This review is for the code your team developed in Week 7 and 8.
I apologize for this delayed code review (should have been given last Friday but I got really sick...).
As compensation, I will add one extra code review in Week 10 (around Thursday).

Overall, really great progress and good code quality too! 
Just 2 comments:
1. It doesn't seem that your team has a consistent way to handle null. In some classes, you do explicit null check (which is not recommended by Uncle Bob).
And in some classes, you use this mechanism:
```
public void landOn(Player player, GameEngine game) {
   Objects.requireNonNull(player, "Player cannot be null");
   Objects.requireNonNull(game, "GameEngine cannot be null");
   }
```
I suggest refactor the explicit null check to the above.

2. GameEngine and Player classes have magic number issues (see explaination here: https://checkstyle.sourceforge.io/checks/coding/magicnumber.html)

Good job! Look forward to seeing your final product :)!!

## Week 6 Code Review
I have read every line of production code currently in the main branch. A couple of things:
1. In the Dice class, 
- there are magic numbers that should be improved
2. In the Player class,
- same magic number issue
- There is a chunk of comment that be removed, even though the message is correct :)
"
  // this is bad practice we should not be using checking with magic numbers th emax bound should be the length of the baord class tiles
  // just for testing purposes we will assume there are 32 tiles on the board and the jail tile is at position 10"
- jailTurnCount and active are currently not being used by any code. Be careful. This is a sign of possible violation of TDD.
3. delete .gitkeeo from the model package now that there are meaningful files under the folder.

Otherwise, good job!
Look forward to reviewing more of your domain logic in the next review.

Please approve and merge the PR once the team has read the feedback. Thanks!

