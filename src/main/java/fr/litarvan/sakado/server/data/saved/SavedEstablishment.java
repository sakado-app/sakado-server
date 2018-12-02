package fr.litarvan.sakado.server.data.saved;

public class SavedEstablishment
{
    private String name;
    private SavedStudentClass[] studentClasses;

    public SavedEstablishment(String name, SavedStudentClass[] studentClasses)
    {
        this.name = name;
        this.studentClasses = studentClasses;
    }

    public String getName()
    {
        return name;
    }

    public SavedStudentClass[] getStudentClasses()
    {
        return studentClasses;
    }
}
