package net.sf.memoranda;

public interface INoteListener {
  void noteChange(INote note, boolean toSaveCurrentNote);
}
