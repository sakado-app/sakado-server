package fr.litarvan.sakado.server.pronote;

public class User
{
    private Pronote pronote;
    private Cours[] edt;

    User(Pronote pronote)
    {
        this.pronote = pronote;
    }

    public void update()
    {

    }

    public Cours[] getEDT()
    {
        if (edt == null)
        {
            update();
        }

        return edt;
    }
}
