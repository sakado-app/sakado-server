/*
 *  Sakado, an app for school
 *  Copyright (c) 2017-2018 Adrien 'Litarvan' Navratil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package fr.litarvan.sakado.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.litarvan.commons.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    private static final Logger log = LogManager.getLogger(Main.class);

    private static Injector injector;

    public static void main(String[] args)
    {
        log.info("Creating Sakado Server...");

        injector = Guice.createInjector(new SakadoServerGuiceModule());
        App app = injector.getInstance(App.class);

        log.info("Starting...");
        System.out.println();

        app.start();
    }

    public static Injector injector()
    {
        return injector;
    }
}
