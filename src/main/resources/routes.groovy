import fr.litarvan.sakado.server.SakadoServer

/**
 * The Application HTTP Routes
 *
 * Here are defined the different requests your app can
 * handle.
 * > See https://paladin-framework.github.io/paladin-docs/Routing.html
 */

// Authentication
group '/auth', {
    post '/login'
    post '/refresh'
    post '/logout'
}, [
    action: 'auth'
]

get '/version', {
    SakadoServer.VERSION
}