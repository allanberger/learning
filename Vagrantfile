# -*- mode: ruby -*-
# vi: set ft=ruby :

ENV["LC_ALL"] = "en_US.UTF-8"
ENV["LANGUAGE"] = "en_US:"
ENV["LC_CTYPE"]	= "UTF-8"
ENV["LANG"] = "en_US"

Vagrant.configure(2) do |config|
  config.vm.box = "bento/ubuntu-16.04"

  config.vm.network "private_network", ip: "10.61.6.31"
  # nRepl Server port forwarding
  config.vm.network "forwarded_port", guest: 8888, host: 8888
  # PostgreSQL Server port forwarding
  config.vm.network "forwarded_port", guest: 5432, host: 15432
  config.vm.hostname = "learning"

  config.vm.synced_folder ".", "/learning"

  config.vm.provision "shell", path: "provision.sh"
end
