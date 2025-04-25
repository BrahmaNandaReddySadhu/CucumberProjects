@Button @Web
Feature: As a user click on button and verify text after perform the action

  @Negative @Button_001
  Scenario: checking buttons with dynamic click
    Given i launch "https://www.tutorialspoint.com/selenium/practice/buttons.php" website
    When i click on "double click me" button
    Then i should not see message as "You have done a dynamic click" for "double click me" button

  @Positive @Button_002
  Scenario: checking buttons with single click
    Given i launch "https://www.tutorialspoint.com/selenium/practice/buttons.php" website
    When i click on "click me" button
    Then i should see message as "You have done a dynamic click4" for "click me" button

  @Positive @Button_003
  Scenario: checking buttons with double click
    Given i launch "https://www.tutorialspoint.com/selenium/practice/buttons.php" website
    When i click on "Double Click Me" button
    Then i should see message as "You have Double clicked" for "double click me" button
