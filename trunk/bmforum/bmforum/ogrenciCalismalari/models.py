from django.db import models
from bmforum.forum.models import Topic, Entry

academic_titles = (
                   (1, "arastirma gorevlisi" ),
                   (2,"ogretim gorevlisi" ),
                   (3, "yardimci docent doktor" ),
                   (4, "docent doktor" ),
                   (5, "profesor doktor" ),
)

class Professor(models.Model):
    name = models.CharField(max_length=30,verbose_name="Ogretmen_ismi")
    academicTitle =  models.IntegerField(choices= academic_titles)
    
    def __unicode__(self):
        return self.name
    
    
class Course(models.Model):
    name = models.CharField(max_length=50,verbose_name="Ders_ismi")
    term = models.CharField(max_length=10,verbose_name="Ders_donemi")
    credits = models.IntegerField()
    explanation = models.TextField(verbose_name="Ders_icerigi")
    courseBy = models.ForeignKey(Professor, blank=False, null=False, verbose_name = "Dersin_ogretmeni")
    isHidden = models.BooleanField(default=False)
    
    def __unicode__(self):
        return self.name
    
class Extra(models.Model):
    file = models.FileField(upload_to="ogrenci_calismalari/uploads", verbose_name="Dosyalar")
    fileName = models.CharField(max_length=30,verbose_name="Dosya_Ismi")
    isHidden = models.BooleanField(default=False)
    description = models.TextField(verbose_name="Dosya_icerigi")
    
    def __unicode__(self):
        return self.fileName
    
class LectureNote(models.Model):
    year = models.DateField(blank=False, null=False)
    extra = models.ForeignKey(Extra,blank=True, null=True,verbose_name="Iliskili_Dosya") 
    course = models.ForeignKey(Course, blank=False, null=False, verbose_name="Ait_Oldugu_ders")
    isHidden = models.BooleanField(default=False)
    name = models.ForeignKey(Topic, blank=False, null=False, verbose_name="Iliskili_baslik")
    description = models.ForeignKey(Entry, blank=False, null=False, verbose_name="Iliskili_girdi")

class Exam(models.Model):
    year = models.DateField(blank=False, null=False)
    extra = models.ForeignKey(Extra,blank=True, null=True,verbose_name="Iliskili_Dosya")
    course = models.ForeignKey(Course, blank=False, null=False, verbose_name="Ait_Oldugu_ders")
    isHidden = models.BooleanField(default=False)
    name = models.ForeignKey(Topic, blank=False, null=False, verbose_name="Iliskili_baslik")
    description = models.ForeignKey(Entry, blank=False, null=False, verbose_name="Iliskili_girdi")
    
class Project(models.Model):
    year = models.IntegerField(blank=False, null=False)
    website = models.URLField()
    doneBy = models.CharField(max_length=60, verbose_name="Proje_gelistiricileri")
    extra = models.ForeignKey(Extra,blank=True, null=True,verbose_name="Iliskili_Dosya")
    course = models.ForeignKey(Course, blank=False, null=False, verbose_name="Ait_Oldugu_ders")
    isHidden = models.BooleanField(default=False)
    name = models.ForeignKey(Topic, blank=False, null=False, verbose_name="Iliskili_baslik")
    description = models.ForeignKey(Entry, blank=False, null=False, verbose_name="Iliskili_girdi")

