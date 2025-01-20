const express = require('express');
const path = require('path');
const Docker = require('dockerode');
const app = express();
const docker = new Docker();
const bodyParser = require('body-parser');

app.use(bodyParser.json());

// Configurer le moteur de vue EJS
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Servir les fichiers statiques (CSS, JS, images)
app.use(express.static(path.join(__dirname, 'public')));

// Route pour afficher la liste des conteneurs
app.get('/containers', (req, res) => {
    docker.listContainers({ all: true }, (err, containers) => {
        if (err) {
            return res.status(500).send("Erreur lors de la récupération des conteneurs");
        }
        res.render('containers', { containers: containers });
    });
});

// Route pour démarrer un conteneur
app.post('/containers/start', (req, res) => {
    const container = docker.getContainer(req.body.id);
    container.start((err) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ message: 'Erreur lors du démarrage du conteneur.' });
        }
        res.json({ message: 'Conteneur démarré avec succès.' });
    });
});

// Route pour arrêter un conteneur
app.post('/containers/stop', (req, res) => {
    const container = docker.getContainer(req.body.id);
    container.stop((err) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ message: 'Erreur lors de l\'arrêt du conteneur.' });
        }
        res.json({ message: 'Conteneur arrêté avec succès.' });
    });
});

// Lancer le serveur sur le port 3000
const port = 3000;
app.listen(port, () => {
    console.log(`Server running on http://localhost:${port}`);
});
