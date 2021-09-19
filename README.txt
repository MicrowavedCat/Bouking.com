Louka DOZ --- Julien CARCAU


Les manipulations suivantes ont été réalisées sur un système Debian 11 avec Java 11.


##### Installation de MariaDB et importation de la base de données #####
    sudo apt install mariadb-server
    sudo mysql_secure_installation
        définir un nouveau mot de passe root et conserver les autres choix par défaut

    En étant toujours connecté sur la machine hébergeant MariaDB (si différente), se connecter à la base en tant que root :
    mysql -u root -p

    Exécuter le contenu de "create_database.sql", présent à la racine du projet, puis revenir au terminal (bash).

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

Après avoir enregistré ce fichier, attendez quelques instants que les modifications soient prises en compte ; si vous utilisez GNOME comme environnement de bureau, le lanceur d'Eclipse Photon devrait apparaître en une dizaine de secondes dans votre menu.



##### Installation et configuration du projet #####
Extraire le contenu du dossier compressé "workspace_content.zip" dans votre workspace.

Après ouverture, plusieurs projets devraient être apparus sur la gauche.

Dans "SNCF_Rest_Services/src/model/Database.java", "Camrail_Rest_Services/src/model/Database.java" et "Transgabonais_Rest_Services/src/model/Database.java", modifier les variables statiques "URL", "USER" et "PASSWORD" en fonction de la configuration de votre base mariadb.
Puis, ouvrir le fichier "WS_Bouking/Java Resources/src/model/Database.java" et modifier la variable statique FOLDER pour pointer correctement sur le dossier "databaseTables/", présent à la racine du projet.

Ensuite, pour les 3 projets REST : SNCF_Rest_Services, Camrail_Rest_Services et Transgabonais_Rest_Services, faire un clic droit sur le projet, accéder à "Properties" > "Java Build Path" > "Libraries" > "Classpath". Ici, éditer les chemins de "org.restlet.jar" et "mariadb-java-client-2.7.4.jar" afin qu'ils pointent sur ceux présents dans le dossier vers "jars/" à la racine du projet.

Afin de configurer Tomcat, cliquer sur Servers (dans la partie basse), puis "No servers are available. Click this link to create a new server...", rechercher "Tomcat v9.0 Server" puis cliquer sur "Finish".



##### Lancement du projet #####
Clic droit sur "src/routing/RESTDistributor.java" > Run As > Java Application, et ceci pour les 3 APIs REST : "SNCF_Rest_Services", "Camrail_Rest_Services" et "Transgabonais_Rest_Services"
Puis clic droit sur le projet WS_Bouking > Run As > Run On Server > sélectionner "Tomcat v9.0 Server at localhost", puis Next. Ici, vérifier que WS_Boukings est bien dans la colonne Configured, s'il est dans Available, l'ajouter dans Configured. Enfin, cliquer sur Finish.

Vous pouvez lancer un client de test en cliquant sur "BoukingsClient/Java Resources/src/app/Main.java" > Run as > Java application.
Vous pouvez utiliser les identifiants suivants : 
	- login : "louka.doz@ensiie.fr", mot de passe : "louka"
	- login : "julien.carcau@ensiie.fr", mot de passe : "julien"



Auto-évaluation (datant d'avant l'oral) :
# 	Requierments                                                                    Marks (20)
1 	Create REST Train Filtering service B                                           5/6
        Nous n'avons fait qu'un seul serveur REST pour le moment
2 	Create SOAP Train Booking service A                                             3/4
        L'authentification existe mais aucune vérification n'est effectuée par la suite afin de vérifier que l'utilisateur s'est bel et bien connecté
3 	Interaction between two services                                                4/4
4 	Test with Web service Client (instead of using Eclipse's Web service Explorer)  2/2
5 	Work with complex data type (class, table, etc.)                                2/2
6 	Work with database (in text file, xml, in mysql, etc.)                          1/2
        Nous n'avons pas réussi à faire communiquer la partie SOAP avec la base de données
