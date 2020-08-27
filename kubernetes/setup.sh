#!/usr/bin/env bash
# https://matthewpalmer.net/kubernetes-app-developer/articles/guide-install-kubernetes-mac.html

/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"

brew tap caskroom/cask

brew install minikube
brew cask install docker --no-quarantine
brew cask install virtualbox --no-quarantine


sysctl -a | grep -E --color 'machdep.cpu.features|VMX'
