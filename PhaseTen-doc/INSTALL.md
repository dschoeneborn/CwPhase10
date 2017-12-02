## Installation

### Checkout SourceCode

Clonse Source code via:
```
git clone https://gitlab.com/mettke/phaseten.git
```
or 
```
git clone git@gitlab.com:mettke/phaseten.git
```

### Setup Eclipse

* Download *Eclipse JEE* from: https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen1a
* Extract Eclipse

### Setup GlassFish

* Download *GlassFish 4.1.2 - Full Platform* from https://javaee.github.io/glassfish/download
* Extract GlassFish

### Install Eclipse Plugin

* Goto *Help* -> *Eclipse Marketplace...*
* Search for *GlassFish Tools*
* Press Install
* (Optional) Select *Java EE 7 Documentation*
* Press *Confirm >*
* Accept License
* Trust Certificate
* Restart Eclipse after Installation finished

### Setup GlassFish Server
* Goto *Window* -> *Show View* -> *Servers*
* Click *No servers available...*
* Select GlassFish*
* Use *GlassFish* as *Server name*
* Press *Next >*
* Select *GlassFish location*
* Select *Java location*
* Make sure *Name* equals *GlassFish*
* Press *Next >*
* Make sure to use *domain1* under *Domain path*
* Press *Next >*
* Add *PhaseTen-ear*
* Press *Finish*
