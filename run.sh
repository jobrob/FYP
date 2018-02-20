cd bin

if [ -z "$1" ]; then
	java ForceDirected.Eades3D
else
	java ForceDirected.Eades3D "$1"
fi
