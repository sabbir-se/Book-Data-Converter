# Book Data Converter

It will convert book information of one text format to another text format. Book of data is in plain text or in json format. It takes input of a file name & it has also a configuration file (**book-info-converter.properties**) where user can set the target text format. In **resource** folder, I stored some sample input files in both format. 

    Run BookDataConvert.java file to see the output. Before run this file:
* You have to pass the **filepath** as a argument.
* You have to set the **targetFormat** into properties file.

I also create Junit test cases for this project. Here, you can find success and error test cases. Following test cases occured for both (success & error):
* Read data file
* Read properties file
* Convert text to json format
* Convert json to text fromat
* Detect the data file format
* Convert the data file into output format

###END