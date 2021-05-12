import sys
import subprocess

compile = [
    'javac', 
    'Aguile.java',
    'FeatureManager.java', 
    'FeatureBuilderMindow.java', 
    'FeatureManagingError.java',
    'FeaturePane.java',
    'Flags.java',
    'JSONData.java',
    'JSONError.java',
    'JSONio.java',
    'PrettyDrawer.java',
    'StateError.java',
    'Task.java',
    'TaskCreationError.java',
    'FileViewer.java',
    'FileViewerTester.java',
    'FileViewerPane.java',
    'FileViewingError.java',
    'ErrorMindow.java',
    'ErrorTester.java'
]

result = subprocess.run(compile).returncode
if result == 0:
    args = sys.argv[1:]
    if args[0] == 'Aguile':
        subprocess.run(['java', 'Aguile'])
    elif args[0] == 'Feature_Tester':
        subprocess.run(['java', 'Feature_Tester'])
    elif args[0] == 'FileViewerTester':
        subprocess.run(['java', 'FileViewerTester', args[1]])
    elif args[0] == 'ErrorTester':
        subprocess.run(['java', 'ErrorTester'])
