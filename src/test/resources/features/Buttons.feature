Feature: As a user click on button and verify text after perform the action

  @sanity
  Scenario: checking buttons
    Given i launch "https://www.tutorialspoint.com/selenium/practice/buttons.php" website
    When i click on "click me" button
    Then i should see message as "You have done a dynamic click2"