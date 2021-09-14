Louka DOZ --- Julien CARCAU


Les manipulations suivantes ont été réalisées sur un système Debian 11 avec Java 11.


##### Installation de MariaDB : #####
    sudo apt install mariadb-server
    sudo mysql_secure_installation
        définir un nouveau mot de passe root et conserver les autres choix par défaut

    En étant toujours connecté sur la machine hébergeant MariaDB (si différente), se connecter à la base en tant que root :
    mysql -u root -p

    Exécuter le contenu de "create_database.sql" puis revenir au terminal (bash).

    ==> Dans le cas d'une base de données sur une machine dédiée, différente de celle sur laquelle tourneront les webservices :
    Exécuter "sudo sed -i 's/^.*bind-address.*$/bind-address            = 0.0.0.0/' /etc/mysql/mariadb.conf.d/50-server.cnf". Attention, cette commande a pour but de rendre accessible la base à d'autres personnes dans le même réseau, il faut donc veiller à utiliser un mot de passe fort.
    À l'avenir, il ne sera donc plus nécessaire d'être connecté directement sur le serveur afin de pouvoir accéder à la base de données, mais il faudra en revanche préciser son IP par un "-h" suivi de l'IP, par exemple :
    mysql -h 192.168.1.2 -u wsclient -p



##### Installation d'Eclipse Photon #####
Télécharger le .tar.gz d'Eclipse Photon ici : https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/photon/R/eclipse-jee-photon-R-linux-gtk-x86_64.tar.gz
Le décompresser, par exemple, dans "/home/USER" où USER est votre login : "tar zxvf eclipse-jee-photon-R-linux-gtk-x86_64.tar.gz".
Afin de pouvoir le lancer plus facilement, il est possible de créer un fichier .desktop qui permettra de lui créer un lanceur. Pour ce faire, mettre le bloc de texte qui suit dans un fichier "~/.local/share/applications/eclipse_photon.desktop" :

[Desktop Entry]
Encoding=UTF-8
Version=1.0
Type=Application
Terminal=false
Exec=/home/USER/eclipse/eclipse
Name=Eclipse Photon
Icon=/home/USER/eclipse/icon.xpm

où USER est toujours votre login.

Après avoir enregistré ce fichier, attendez quelques instants que les modifications soient prises en compte, si vous utilisez GNOME comme environnement de bureau, les le lanceur d'Eclipse Photon devrait apparaître en une dizaine de secondes dans votre menu.






Installer restlet framework (http://www-inf.telecom-sudparis.eu/SIMBAD/courses/doku.php?id=teaching_assistant:web_services:restful_helloworld).

Changer tous les chemin absolus vers le jar restlet.
