# autoframework
A framework based on selenium and testng

1. You can find all bease operations with selenium driver such as Click, Type, Select etc. in BasePage.java
2. Use Init.java to get webdrivers for Chrome, Firefox and IE
3. All element can be maintained like json format in element.yaml under 'project root dir/testData'
4. Also created a dataProvider method to accept Map<Object, Object> for test data stored in Excel which you are going to use in your testing. Currently the test data file name is hard coded as Test Data.xlsx under 'project root dir/testData'
5. Be aware of the jdk version specified in the pom.xml file, as I'm using jdk 12, so I put 12 there, for previous version of jdk please change to 1.8 for jdk 8, 1.9 for jdk 9 etc.
