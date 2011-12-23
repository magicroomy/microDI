MicroDI is an extremely small library to provide dependency injection for Java applications.

Why use it?

There are several DI frameworks out there. Why would i need another one to consider?
The very simple reason why i created microDI was its size. I work with Spring and i also checked out Weld for DI in a little Java App i wrote. 
My application is no distributed enterprise app, but a little tiny Swing application. It's size was around a few 100kB, 
but the necessary jars for Spring or Weld(SE) boosted the necessary over all size to megabytes.
That was the reason to start a little DI tool myself.

What are the potential use cases you would use or not use MicroDI?

Do not use it if:
- you are developing an JEE application. Use CDI here. Great stuff.
- You already have Spring, Weld or any other DI framework running. Of course you will use those frameworks for DI.

Consider using it if:
- You create an application not mentioned under "Do not use it if:" :-)

What is it?
MicroDI is a DI framework. It is not a container. Being a container it would have a main method and you would start your application by calling this main method.
Your application would then have to be kind of "deployed".
Currently MicroDI expects you to write the main method and to properly initialize it in order to work. The boot procedure is very similar to Spring or Weld.




