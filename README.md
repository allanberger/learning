vagrant-clojure
=====================

A repository with a Clojure ready Vagrant Environment.  

**Setup Requirements**  
- VirtualBox 5.1.x  
- Vagrant 1.9.x  

**Pre-Installed on the VM**  
- Java Runtime (default-jre)  
- Leiningen (https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein)  


Update your Vagrant Synced Folder by updating this line in to your preferred paths `Vagrantfile`:  
`config.vm.synced_folder "/Users/allanberger/Projects/learning", "/vagrant"`
