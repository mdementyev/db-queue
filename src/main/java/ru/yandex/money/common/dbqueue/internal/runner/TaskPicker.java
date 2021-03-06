package ru.yandex.money.common.dbqueue.internal.runner;

import ru.yandex.money.common.dbqueue.api.QueueConsumer;
import ru.yandex.money.common.dbqueue.api.TaskLifecycleListener;
import ru.yandex.money.common.dbqueue.api.TaskRecord;
import ru.yandex.money.common.dbqueue.internal.MillisTimeProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Класс, обеспечивающий выборку задачи из очереди
 *
 * @author Oleg Kandaurov
 * @since 19.07.2017
 */
@SuppressWarnings("rawtypes")
class TaskPicker {

    @Nonnull
    private final PickTaskDao pickTaskDao;
    @Nonnull
    private final TaskLifecycleListener taskLifecycleListener;
    @Nonnull
    private final MillisTimeProvider millisTimeProvider;
    @Nonnull
    private final RetryTaskStrategy retryTaskStrategy;

    /**
     * Конструктор
     *
     * @param pickTaskDao           поставщик задач из очереди
     * @param taskLifecycleListener слушатель жизненного цикла задачи в очереди
     * @param millisTimeProvider    поставщик текущего времени
     * @param retryTaskStrategy     стратегия откладывания задачи при повторном выполнении
     */
    TaskPicker(PickTaskDao pickTaskDao,
               TaskLifecycleListener taskLifecycleListener,
               MillisTimeProvider millisTimeProvider,
               RetryTaskStrategy retryTaskStrategy) {
        this.pickTaskDao = requireNonNull(pickTaskDao);
        this.taskLifecycleListener = requireNonNull(taskLifecycleListener);
        this.millisTimeProvider = requireNonNull(millisTimeProvider);
        this.retryTaskStrategy = requireNonNull(retryTaskStrategy);
    }

    /**
     * Выбрать задачу из очереди
     *
     * @param queueConsumer очередь для выборки
     * @return задача или null если отсуствует
     */
    @Nullable
    TaskRecord pickTask(@Nonnull QueueConsumer queueConsumer) {
        requireNonNull(queueConsumer);
        long startPickTaskTime = millisTimeProvider.getMillis();
        TaskRecord taskRecord = pickTaskDao.getTransactionTemplate()
                .execute(status -> pickTaskDao.pickTask(queueConsumer.getQueueConfig().getLocation(),
                        retryTaskStrategy));
        if (taskRecord == null) {
            return null;
        }
        taskLifecycleListener.picked(pickTaskDao.getShardId(), queueConsumer.getQueueConfig().getLocation(),
                taskRecord, millisTimeProvider.getMillis() - startPickTaskTime);
        return taskRecord;
    }


}
