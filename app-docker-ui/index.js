const Docker = require('dockerode');
const docker = new Docker(); // Crée une instance de Dockerode

// Lister les conteneurs en cours d'exécution
docker.listContainers({ all: true }, function(err, containers) {
    if (err) {
        console.error("Erreur lors de la liste des conteneurs", err);
        return;
    }

    console.log("Conteneurs en cours d'exécution :");
    containers.forEach(container => {
        console.log(container.Id);
    });
});
