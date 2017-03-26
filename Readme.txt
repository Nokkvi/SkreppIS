1. Opna cmd og skrifa ip config. Finndu local ip töluna. 192.168.???.???

2. Opnar forritið í einhverju django umhverfi(mælum með pycharm). Til að geta keyrt bakendann þarf að keyra pip skipanirnar: 
	pip install djangorestframework, pip install markdown, pip install django-filter, pip install djangorestframework-jwt

3. Opnar Skreppís í pycharm og skrifar í run: py manage.py runserver 192.168.???.???:8000

4. Nú er hægt að opna skreppIsUI folderinn í android studio. Þar þarftu að breyta ip addressum í LoginActivity og RegiserActivity
	í ip töluna sem þú notaðir til að keyra server.