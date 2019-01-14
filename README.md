# Sainsburys
The purpose of this project is to scrap Sainsbury's grocery site's- Berries, Cherries:
"https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html"
and give an ouput in json format.
To do this I used HtmlUnit API to scrap the site web, jackson-databind, jackson-annotations,
json-simple to create the json format.
I used junit-jupiter-api to do the test and finally I used log4j for logging.
To run the application you can use two ways: using an IDE or through the commad shell using this commad in shell:
java -cp target/Sainsburys/Sainsburys.jar com.grocery.sainsburys.SainsburysProducts "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html"
But before executing the command you need to into project folder: cd path/to/AppFolder
