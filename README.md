# Book Data Converter

It will convert book information of one text format to another text format. Book of data is in plain text or in json format. It takes input of a file name & it has also a configuration file (**book-info-converter.properties**) where user can set the target text format. In **resource** folder, I stored some sample input files in both format. Run **BookDataConvert.java** file to see the output. When run this file you have to pass the **FilePath** as a argument.

I also create Junit test cases for this project:
* Here you can find both right and wrong test cases.
* To see some error test cases, you have to commnet out some methods which name format like this (***Error()**).
